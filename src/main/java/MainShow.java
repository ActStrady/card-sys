import java.math.BigDecimal;
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
            System.out.println("-----------------3、查询------------------");
            System.out.println("-----------------4、退出------------------");
            int cho = scanner.nextInt();
            switch (cho) {
                case 1:
                    System.out.println("--------------请输入充值金额-------------");
                    BigDecimal money = scanner.nextBigDecimal();
                    // 充值
                    if (cardService.recharge(money, sId)) {
                        System.out.println("-------------充值成功-------------");
                    } else {
                        System.out.println("--------您绑定银行卡的金额不足!--------");
                        System.out.println("--------银行卡的金额为：" + cardService.getBalance(sId) + "--------");
                    }
                    break;
                case 2:
                    System.out.println("--------------商品列表--------------");
                    System.out.println("-----------1.橡皮 单价 1.0----------");
                    System.out.println("-----------2.本子 单价 1.5----------");
                    System.out.println("-----------3.圆珠笔 单价 2.0--------");
                    BigDecimal reaser = new BigDecimal(1.0);
                    BigDecimal book = new BigDecimal(1.5);
                    BigDecimal pen = new BigDecimal(2.0);
                    // 输入错误的可以重新输入
                    while (true) {
                        int chos = scanner.nextInt();
                        switch (chos) {
                            case 1:
                                System.out.println("--------------要购买的数量--------------");
                                BigDecimal num = scanner.nextBigDecimal();
                                if (cardService.shopping(sId, reaser, num)) {
                                    System.out.println("--------------购买成功--------------");
                                } else {
                                    System.out.println("--------------余额不足!--------------");
                                    System.out.println("---------余额为" + cardService.getMoney(sId) + "----------");
                                }
                                break;
                            case 2:
                                System.out.println("--------------要购买的数量--------------");
                                BigDecimal numBook = scanner.nextBigDecimal();
                                if (cardService.shopping(sId, book, numBook)) {
                                    System.out.println("--------------购买成功--------------");
                                } else {
                                    System.out.println("--------------余额不足!--------------");
                                    System.out.println("---------余额为" + cardService.getMoney(sId) + "----------");
                                }
                                break;
                            case 3:
                                System.out.println("--------------要购买的数量--------------");
                                BigDecimal numPen = scanner.nextBigDecimal();
                                if (cardService.shopping(sId, pen, numPen)) {
                                    System.out.println("--------------购买成功--------------");
                                } else {
                                    System.out.println("--------------余额不足!--------------");
                                    System.out.println("---------余额为" + cardService.getMoney(sId) + "----------");
                                }
                                break;
                            default:
                                System.out.println("输入错误！");
                        }
                        // 输入正确退出
                        if (chos == 1 || chos ==2 || chos ==3) {
                            break;
                        }
                    }
                    break;
                case 3:
                    System.out.println("---------余额为：" + cardService.getMoney(sId) + "---------");
                    break;
                case 4:
                    System.exit(0);
                default:
                    System.out.println("输入错误！");
            }
        }
    }

    private static void show() {
        System.out.println("----------------------- ---------------");
        System.out.println("---------------饭卡系统-----------------");
        System.out.println("---------------请输入学号---------------");
    }
}
