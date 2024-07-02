/*
 * This source file was generated by the Gradle 'init' task
 */
package bitcamp.project2;

import bitcamp.project2.command.CreateCommand;
import bitcamp.project2.command.ManagementCommand;
import bitcamp.project2.command.ReadCommand;
import bitcamp.project2.command.TodoCommand;
import bitcamp.project2.util.Print;
import bitcamp.project2.util.PromptTodo;

public class App {

    static String mainTitle = "Todo List";
    static String[] mainMenus = {"Todo List", "Todo 생성", "Todo 관리", "Todo 조회"};

    public static void main(String[] args) {
        TodoCommand.init();
        while (true) {

            Print.printHaveTodoList(TodoCommand.todos);
            Print.printTitle(mainTitle);
            Print.printMenus(mainMenus);

            int menuNo = PromptTodo.inputInt("메인 >>");

            switch (menuNo) {
                case 1:
                    // 미완성 메서드
                    Print.printTodoList(TodoCommand.todos);
                    break;
                case 2:
                    CreateCommand.createTodo();
                    break;
                case 3:
                    ManagementCommand.management();
                    break;
                case 4:
                    ReadCommand.readTodo();
                    break;
                default:
                    System.out.println("[System] 올바른 메뉴를 선택해 주세요.");
            }
        }
    }


}

