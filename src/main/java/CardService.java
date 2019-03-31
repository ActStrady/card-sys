import java.math.BigDecimal;
import java.sql.*;

/**
 * @program: card-sys
 * @description: 饭卡功能实现
 * @author: ActStrady
 * @create: 2019-03-29 15:30
 **/
public class CardService {
    private static final String URL = "jdbc:mysql://34.80.78.196?useSSL=false";
    private static final String USER = "root";
    private static final String PASSWORD = "Qw147258369...";

    /**
     * 判断登录名是否存在
     *
     * @param sId 学号
     * @return true false
     */
    public Boolean login(String sId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Boolean result = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            String sql = "select id from card_sys.card where s_id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, sId);
            resultSet = preparedStatement.executeQuery();
            result = resultSet.first();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            close(connection, preparedStatement, resultSet);
        }
        return result;
    }

    /**
     * 判断账号密码是否正确
     *
     * @param sId      学号
     * @param password 密码
     * @return true false
     */
    public Boolean login(String sId, String password) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Boolean result = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            String sql = "select id from card_sys.card where s_id = ? and password = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, sId);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();
            result = resultSet.first();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            close(connection, preparedStatement, resultSet);
        }
        return result;
    }

    /**
     * 充值功能实现
     *
     * @param money 充值金额
     * @param sId   学号
     */
    public void recharge(double money, String sId) {
        Connection connection = null;
        PreparedStatement addStatement = null;
        PreparedStatement subStatement = null;
        ResultSet balanceSet = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            BigDecimal balance = getBalance(sId);
            BigDecimal decimalMoney = BigDecimal.valueOf(money);
            // 判断银行卡余额是否足够支付，够就执行卡金额加，银行卡余额减
            if (balance.compareTo(decimalMoney) > 0) {
                String addSql = "update card_sys.card set money = money + ?";
                String subSql = "update card_sys.card set balance = balance - ?";
                addStatement = connection.prepareStatement(addSql);
                addStatement.setBigDecimal(1, decimalMoney);
                subStatement = connection.prepareStatement(subSql);
                subStatement.setBigDecimal(1, decimalMoney);
                addStatement.executeUpdate();
                subStatement.executeUpdate();
            } else {
                System.out.println("--------您绑定银行卡的金额不足!--------");
                System.out.println("--------银行卡的金额为：" + balance + "--------");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            close(connection, addStatement, balanceSet);
            close(connection, subStatement, balanceSet);
        }
    }

    /**
     * 查询姓名
     *
     * @param sId 学号
     * @return 余额
     */
    public String getName(String sId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet nameSet = null;
        String name = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            String sql = "select name from card_sys.card where s_id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, sId);
            nameSet = preparedStatement.executeQuery();
            nameSet.next();
            name = nameSet.getString(1);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            close(connection, preparedStatement, nameSet);
        }
        return name;
    }

    /**
     * 查询余额
     *
     * @param sId 学号
     * @return 余额
     */
    public BigDecimal getBalance(String sId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet balanceSet = null;
        BigDecimal balance = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            // 查询银行卡余额
            String sql = "select balance from card_sys.card where s_id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, sId);
            balanceSet = preparedStatement.executeQuery();
            balanceSet.next();
            balance = balanceSet.getBigDecimal(1);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            close(connection, preparedStatement, balanceSet);
        }
        return balance;
    }

    /**
     * 查询卡金额
     *
     * @param sId 学号
     * @return 金额
     */
    public BigDecimal getMoney(String sId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet moneySet = null;
        BigDecimal money = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            // 查询银行卡余额
            String sql = "select money from card_sys.card where s_id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, sId);
            moneySet = preparedStatement.executeQuery();
            moneySet.next();
            money = moneySet.getBigDecimal(1);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            close(connection, preparedStatement, moneySet);
        }
        return money;
    }

    /**
     * 关闭全部
     *
     * @param connection        连接
     * @param preparedStatement 预处理
     * @param resultSet         结果集
     */
    private void close(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
            }
        }
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
