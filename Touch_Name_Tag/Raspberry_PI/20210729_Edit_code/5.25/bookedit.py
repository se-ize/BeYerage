import os
import sys
folder = os.path.dirname(os.path.abspath(__file__))
book = os.path.join(folder,'input.txt')

class bookadd:
    def add(self):
        print("도서 추가를 진행합니다.")
        name = input("추가하려는 도서의 이름을 알려주세요:\n  ")
        writer = input("추가하려는 도서의 저자를 알려주세요:\n  ")
        day = input("추가하려는 도서의 출판연도를 알려주세요:\n예>2002\n  ")
        publisher = input("추가하려는 도서의 출판사를 알려주세요:\n  ")
        feild = input("추가하려는 도서의 장르를 알려주세요:\n  ")

        print("추가되는 도서는 ",name,writer,day,publisher,feild," 이다.")
        a = []
        c = name,writer,day,publisher,feild,"\n"
        b = ' '.join(c)
        a.append(b)
        print(a)
        return(a)
class bookchange:
    def change(self,list):
        try:
            print("도서 정보를 수정합니다.\n")
            for a in range(len(list)):
                print(a+1,"번",list[a])
            change = int(input("수정할 도서의 번호를 선택하세요\n"))
            print(list[change-1]," 를(을) 수정하겠습니다.\n 1. 도서명\n 2. 저자\n 3. 출판연도\n 4. 출판사명\n 5. 장르")
            change1 = int(input(" 어느부분을 수정하시겠습니까?\n"))
            lista = list[change-1].split()
            change2 = input(" 무엇으로 수정하시겠습니까?\n")
            lista[change1-1] = change2
            print(lista)
            lista = ' '.join(lista)
            lista += "\n"
            list[change-1] = lista 
            return(list)

        except Exception as ex:
            print("보기의 숫자를 입력해 주세요")
            print(ex,"\n 오류가 발생하였습니다.")

    def trash(self,list):
        try:
            print("도서 정보를 삭제합니다.\n")
            for a in range(len(list)):
                print(a+1,"번",list[a])
            change = int(input("삭제할 도서의 번호를 선택하세요\n"))
            print(list[change-1]," 를(을) 삭제 하겠습니다.")
            del list[change-1]
            return(list)
        except Exception as ex:
            print("보기의 숫자를 입력해 주세요")
            print(ex,"\n 오류가 발생하였습니다.")
