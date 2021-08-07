import os
import sys
folder = os.path.dirname(os.path.abspath(__file__))
book = os.path.join(folder,'input.txt')
import bookedit


class BookResearch:
    def __init__(self):
        with open(book,'r') as f:
                x = list(f.readlines())
                self.booklist = x

    def menu(self):
        while True:
            print("\n\n1. 도서 추가(도서명,저자,출판연도,출판사명,장르 입력) ")
            print("2. 도서 검색 ")
            print("3. 도서 정보 수정 ")
            print("4. 도서 삭제")
            print("5. 현재 총 도서 목록 출력 ")
            print("6. 저장 ")
            print("7. 프로그램 나가기(자동 저장) ")
            print(" ** 되돌아가기 키는 \'p\'입니다.**")

            try:
                menu = int(input("\n원하는 사항의 번호를 입력하세요 : "))
            except Exception as ex:
                print("\n원하는 사항의 번호를 다시 입력해 주세요")
                print(ex," 로 인한 오류가 발생하였습니다.")  
                self.menu() 
            
            if menu == 1:
                book = bookedit.bookadd()
                a = book.add()
                self.booklist += a
            elif menu == 2:
                self.research()
            elif menu == 3:
                booklist = self.booklist
                book = bookedit.bookchange
                b = book.change(self,booklist)
            elif menu == 4:
                booklist = self.booklist
                book = bookedit.bookchange
                b = book.trash(self,booklist)
            elif menu == 5:
                self.readlist()
            elif menu == 6:
                self.copy()
                print("저장하였습니다.")
            elif menu == 7:
                print("\n저장후,프로그램을 종료합니다.")
                self.copy()
                self.end()
            else:
                print ("원하는 사항의 \'번호\'를 입력해주세요.",)
                self.menu()
    def copy(self):
        Book = open(book,'w')
        for s1 in range(0,len(self.booklist)):
            Book.writelines(self.booklist[s1])
        Book.close()

    def research(self):
        t = 0
        try:
            print("  검색방법 \n 1. 도서명 \n 2. 저자 \n 3.출판연도\n 4. 출판사명\n 5. 장르 \n")
            kind = input("원하는 도서의 검색방법의 번호를 고르세요.\n 예> 3 \n")

            if kind == "p":
                self.menu()
            else:
                kind = int(kind)


            if  kind == 1:
                name = input("원하시는 도서의 이름을 입력하세요\n")
                if name == 'p':
                    self.menu()
                x = self.booklist
                for s1 in range(0,len(x)):
                    x1 = list(x[s1].split())
                    if x1[0] == name:
                        print(x1[0:len(x1)])
                        t += 1
                if t == 0:
                    print("입력하신 도서가 존재하지 않습니다.")
                        
            elif kind == 2:
                writer = input("원하는 도서의 저자명을 입력하세요\n")
                if writer == 'p':
                    self.menu()
            
                x = self.booklist
                for s1 in range(0,len(x)):
                    x1 = list(x[s1].split())
                    if x1[1] == writer:
                        print(x1[0:len(x1)])
                        t += 1
                if t == 0:
                    print("입력하신 저자의 도서가 존재하지 않습니다.")          
            elif kind == 3:
                day = input("원하는 도서의 출판연도를 입력하세요\n예> 2002\n")
                if day == 'p':
                    self.menu()
                x = self.booklist
                for s1 in range(0,len(x)):
                    x1 = list(x[s1].split())
                    if x1[2] == day:
                        print(x1[0:len(x1)])
                        t += 1
                if t == 0:
                    print("입력하신 출판연도의 도서가 존재하지 않습니다.")         
            elif kind == 4:
                publisher = input("원하는 도서의 출판사를 입력하세요\n")
                if publisher == 'p':
                    self.menu()
                x = self.booklist
                for s1 in range(0,len(x)):
                    x1 = list(x[s1].split())
                    if x1[3] == publisher:
                        print(x1[0:len(x1)])
                        t += 1
                if t == 0:
                    print("입력하신 출판연도의 도서가 존재하지 않습니다.")   
            elif kind == 5:
                field = input("원하는 도서의 장르를 입력하세요\n")
                if field == 'p':
                    self.menu()
                x = self.booklist
                for s1 in range(0,len(x)):
                    x1 = list(x[s1].split())
                    if x1[4] == field:
                        print(x1[0:len(x1)])
                        t += 1
                if t == 0:
                    print("입력하신 장르의 도서가 존재하지 않습니다.")
            return(x1[0:len(x1)])
                        
        except Exception as ex:
            print(ex,"\n research 오류가 발생하였습니다.")

    def end(self):
        sys.exit(0)

    def readlist(self):
        try:
            print("\n")
            while True:
                x = self.booklist
                for r in x: 
                    if not x:break
                    print(r,end = '')
                break
        except Exception:
            print("파일이 없습니다. 확인해주세요")
while True:
    print(BookResearch().menu())

