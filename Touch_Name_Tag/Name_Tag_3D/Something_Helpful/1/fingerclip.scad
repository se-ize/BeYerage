difference() {
	union() {
		cube([12.5, 12.5/2, 4]);
		translate([12.5/2, 12.5/2, 0]) cylinder(h=4, d=12.5);
	}
	translate([12.5/2, 8, -0.5]) cylinder(h=5, r=1.6, $fn = 12);
}
rotate([0, 0, -90]) Fingers();

module Fingers() {
difference() {
	union() {
	Finger();
	translate([0, 5]) Finger();
	translate([0, 10]) Finger();
}
		translate([6, 6, 4]) rotate([90, 0, 0]) cylinder(r=1.6, h=14, $fn=12, center=true);

}
}

module Finger() {
		hull() {	
			union() {
				translate([6, 2.5, 4]) rotate([90, 0, 0]) cylinder(h=2.5, d=8);
				translate([6, 0, 0]) cube([4, 2.5, 4]);
			}
			translate([-2,0, 0]) cube([2, 2.5, 4]);
		}
}

