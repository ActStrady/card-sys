import java.util.Scanner;

/**
 * @program: card-sys
 * @description: 主界面
 * @author: ActStrady
 * @create: 2019-03-29 15:23
 **/
public class MainShow {
    public static void main(String[] args) {
        // 显示主界面
        show();
        Scanner scanner = new Scanner(System.in);
        // 输入账号密码
        String sId = scanner.next();
        System.out.println("--------------请输入密码----------------");
        String password = scanner.next();

        // 判定账号和密码是否正确
        CardService cardService = new CardService();
        if (cardService.login(sId)) {
            if (cardService.login(sId, password)) {
                System.out.println("------------" + cardService.getName(sId) + " 登录成功-------------");
            } else {
                System.out.println("---------------密码错误------------------");
                System.exit(0);
            }
        } else {
            System.out.println("------------用户不存在-------------");
            System.exit(0);
        }
        // 登录成功
        while (true) {
            System.out.println("----------------请选择你的操作-------------");
            System.out.println("-----------------1、充值------------------");
            System.out.println("-----------------2、购物------------------");
            System.out.println("-----------------3、退出------------------");
            int cho = scanner.nextInt();
            if (cho == 1) {
                System.out.println("------------卡余额为：" + cardService.getMoney(sId) + "------------");
                System.out.println("--------------请输入充值金额-------------");
                double money = scanner.nextDouble();
                // 充值
                if (cardService.recharge(money, sId)) {
                    System.out.println("-------------充值成功-------------");
                    System.out.println("-----------卡余额为：" + cardService.getMoney(sId) + "-----------");
                } else {
                    System.out.println("--------您绑定银行卡的金额不足!--------");
                    System.out.println("--------银行卡的金额为：" + cardService.getBalance(sId) + "--------");
                }

            }
            if (cho == 2) {
                System.out.println("-----------------要买啥-------------------");
            }
            if (cho == 3) {
                System.exit(0);
            }
        }
    }

    public static void show() {
        System.out.println("----------------------- ---------------");
        System.out.println("---------------饭卡系统-----------------");
        System.out.println("---------------请输入学号---------------");
    }
}
