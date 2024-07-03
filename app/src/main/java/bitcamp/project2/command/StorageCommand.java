package bitcamp.project2.command;

import bitcamp.project2.util.Ansi;
import bitcamp.project2.util.Print;
import bitcamp.project2.util.PromptTodo;
import bitcamp.project2.vo.Todo;

public class StorageCommand {

    public static void manageStorage() {
        String[] manageStorageMenus = {"보관함 생성", "보관함 조회", "보관함 수정", "보관함 삭제"};

        while (true) {
            Print.printTitle("보관함 관리");
            Print.printMenus(manageStorageMenus);

            int menuNo = PromptTodo.inputInt("메인/관리/보관함 관리 >>");

            switch (menuNo) {
                case 1:
                    createStorage();
                    break;
                case 2:
                    readStorage();
                    break;
                case 3:
                    updateStorage();
                    break;
                case 4:
                    deleteStorage();
                    break;
                default:
                    System.out.println("[System] 올바른 메뉴를 입력하세요.");
            }
        }
    }

    public static void createStorage() {
        Print.printTitle("보관함 생성");

        while (true) {
            String storageName = PromptTodo.input("보관함 이름 (최대 10Bytes) [0 = 종료] >>");

            if (storageName.getBytes().length > Todo.MAX_LENGTH_STORAGE) {
                Print.printSystem("이름이 너무 깁니다.");
                continue;
            }

            try {
                int num = Integer.parseInt(storageName);
                if (num == 0) {
                    break;
                } else {
                    Todo.addStorageList(Integer.toString(num));
                }
            } catch (NumberFormatException e) {
                Todo.addStorageList(storageName);
            }
        }
    }

    public static void readStorage() {
        Print.printTitle("보관함 조회");

        for (int i = 0; i < Todo.getStorageList().size(); i++) {
            System.out.println(i + 1 + ". " + Todo.getStorageList().get(i));
        }
    }

    public static void updateStorage() {
        Print.printTitle("보관함 이름 수정");

        for (int i = 0; i < Todo.getStorageList().size(); i++) {
            System.out.println(i + 1 + ". " + Todo.getStorageList().get(i));
        }

        int storageIndex = PromptTodo.inputInt("보관함 선택 [0 = 종료] >>") - 1;

        while (true) {
            if (storageIndex == -1) {
                return;
            } else {
                String storageName = PromptTodo.input("보관함 이름 (최대 10Bytes) [0 = 종료] >>");

                if (storageName.getBytes().length > Todo.MAX_LENGTH_STORAGE) {
                    Print.printSystem("이름이 너무 깁니다.");
                    continue;
                }

                try {
                    int num = Integer.parseInt(storageName);
                    if (num == 0) {
                        break;
                    } else {
                        Todo.getStorageList().set(storageIndex, Integer.toString(num));
                    }
                } catch (NumberFormatException e) {
                    Todo.getStorageList().set(storageIndex, storageName);
                }
            }
        }
    }

    public static void deleteStorage() {
        Print.printTitle("보관함 삭제");

        System.out.println(Ansi.RED + "1. " + Todo.getStorageList().get(0) + Ansi.INIT);

        for (int i = 1; i < Todo.getStorageList().size(); i++) {
            System.out.println(i + 1 + ". " + Todo.getStorageList().get(i));
        }

        int storageIndex = PromptTodo.inputInt("보관함 선택 [0 = 종료] >>") - 1;

        if (storageIndex == -1) {
            return;
        } else if (storageIndex == 0) {
            System.out.println("1번 보관함은 삭제할 수 없습니다.");
        } else {
            Todo.getStorageList().remove(storageIndex);

            for (int i = 0; i < TodoCommand.todos.size(); i++) {
                if (TodoCommand.todos.get(i).getStorageIndex() == storageIndex) {
                    TodoCommand.todos.get(i).setStorageIndex(0);
                }
            }
        }
    }
}
