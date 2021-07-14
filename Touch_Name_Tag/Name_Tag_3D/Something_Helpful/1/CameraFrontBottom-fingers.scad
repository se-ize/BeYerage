import("raspberri_pi_camera_case_front_v0.4.2.STL");

// X (despite being called depth)
casedepth = 29.8;

// Y (despite being called width)
casewidth = 28.8;

// Z
caseheight = 10;

translate([casedepth/2 - 12.5/2, 0, 0]) rotate([0, 0, -90]) Fingers();

module Fingers() {
	union() {
		Finger();
		translate([0, 5]) Finger();
		translate([0, 10]) Finger();
	}
}

module Finger() {
	difference() {
		union() {
			translate([10, 2.5, 4]) rotate([90, 0, 0]) cylinder(h=2.5, d=8);
			translate([-2, 0, 0]) cube([12, 2.5, 8]);
			translate([10, 0, 0]) cube([4, 2.5, 4]);
		}
		translate([10, 3, 4]) rotate([90, 0, 0]) cylinder(r=1.6, h=4, $fn=12);
	}
}

