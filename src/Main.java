import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
问题描述
　　授权 (authorization) 是各类业务系统不可缺少的组成部分，系统用户通过授权机制获得系统中各个模块的操作权限。
　　本题中的授权机制是这样设计的：每位用户具有若干角色，每种角色具有若干权限。例如，用户 david 具有 manager 角色，manager 角色有 crm:2 权限，则用户 david 具有 crm:2 权限，也就是 crm 类权限的第 2 等级的权限。
　　具体地，用户名和角色名称都是由小写字母组成的字符串，长度不超过 32。权限分为分等级权限和不分等级权限两大类。分等级权限由权限类名和权限等级构成，中间用冒号“:”分隔。其中权限类名也是由小写字母组成的字符串，长度不超过 32。权限等级是一位数字，从 0 到 9，数字越大表示权限等级越高。系统规定如果用户具有某类某一等级的权限，那么他也将自动具有该类更低等级的权限。例如在上面的例子中，除 crm:2 外，用户 david 也具有 crm:1 和 crm:0 权限。不分等级权限在描述权限时只有权限类名，没有权限等级（也没有用于分隔的冒号）。
　　给出系统中用户、角色和权限的描述信息，你的程序需要回答多个关于用户和权限的查询。查询可分为以下几类：
　　* 不分等级权限的查询：如果权限本身是不分等级的，则查询时不指定等级，返回是否具有该权限；
　　* 分等级权限的带等级查询：如果权限本身分等级，查询也带等级，则返回是否具有该类的该等级权限；
　　* 分等级权限的不带等级查询：如果权限本身分等级，查询不带等级，则返回具有该类权限的等级；如果不具有该类的任何等级权限，则返回“否”。
输入格式
　　输入第一行是一个正整数 p，表示不同的权限类别的数量。紧接着的 p 行被称为 P 段，每行一个字符串，描述各个权限。对于分等级权限，格式为 <category>:<level>，其中 <category> 是权限类名，<level> 是该类权限的最高等级。对于不分等级权限，字符串只包含权限类名。
　　接下来一行是一个正整数 r，表示不同的角色数量。紧接着的 r 行被称为 R 段，每行描述一种角色，格式为
　　<role> <s> <privilege 1> <privilege 2> ... <privilege s>
　　其中 <role> 是角色名称，<s> 表示该角色具有多少种权限。后面 <s> 个字符串描述该角色具有的权限，格式同 P 段。
　　接下来一行是一个正整数 u，表示用户数量。紧接着的 u 行被称为 U 段，每行描述一个用户，格式为
　　<user> <t> <role 1> <role 2> ... <role t>
　　其中 <user> 是用户名，<t> 表示该用户具有多少种角色。后面 <t> 个字符串描述该用户具有的角色。
　　接下来一行是一个正整数 q，表示权限查询的数量。紧接着的 q 行被称为 Q 段，每行描述一个授权查询，格式为 <user> <privilege>，

表示查询用户 <user> 是否具有 <privilege> 权限。
如果查询的权限是分等级权限，则查询中的 <privilege> 可指定等级，
表示查询该用户是否具有该等级的权限；也可以不指定等级，表示查询该用户具有该权限的等级。
对于不分等级权限，只能查询该用户是否具有该权限，查询中不能指定等级。

输出格式
　　输出共 q 行，每行为 false、true，或者一个数字。
false 表示相应的用户不具有相应的权限，true 表示相应的用户具有相应的权限。
对于分等级权限的不带等级查询，如果具有权限，则结果是一个数字，表示该用户具有该权限的（最高）等级。
如果用户不存在，或者查询的权限没有定义，则应该返回 false。

* */

public class Main {

    private static List<String> CATEGORYS = new ArrayList<>();

    private static String[][] ROLES = new String[100][11]; //第一位为角色，后面为权限

    private static String[][] CATEGOPYS_LEVEL = new String[100][11]; //第一位为角色，后面为权限等级，-1表示没有权限；

    private static String[][] USERS = new String[100][11];//第一位为用户，后面为角色

    private static int USER_NUM = 0; //用户数量


    private static String[][] USER_PRIVILEGES = new String[100][101]; //第一位为用户，后面为权限
    private static String[][] USER_LEVEL = new String[100][101]; // 第一位为用户，后面为最大等级


    private static void userToPrivilege(){

        for(int i = 0 ;i< USER_NUM;i++){
            USER_PRIVILEGES[i][0] = USERS[i][0]; //设置第一位为USER
            USER_LEVEL[i][0] = USERS[i][0];
            int j = 1;
            int p = 1;
            while(USERS[i][j]!=null){
                int k = 0;
                while(ROLES[k][0]!=null){
                    if(ROLES[k][0].equals(USERS[i][j])){
                        int q = 1;
                        while (ROLES[k][q]!=null){
                            boolean bl = true;
                            for(int r = 1; r<=p; p++){
                                if(USER_PRIVILEGES[i][r] == ROLES[k][q]){
                                    bl = false;
                                    if(Integer.valueOf(USER_LEVEL[i][r]) <Integer.valueOf(CATEGOPYS_LEVEL[k][q])){
                                        USER_LEVEL[i][r] = CATEGOPYS_LEVEL[k][q];
                                    }
                                }
                            }
                            if(true){
                                USER_PRIVILEGES[i][p] = ROLES[k][q];
                                USER_LEVEL[i][p] = CATEGOPYS_LEVEL[k][q];
                                p++;
                            }
                        }
                    }
                    k++;
                }
                j++;
            }






/*
            int j = 1;
            int p = 1;
            while(ROLES[j][0]!=null){
                if(ROLES[j][0].equals(USERS[i][j])){
                //    int k = 1;
                //    while(ROLES[j][k]!= null){
                    for(int k = 1; k<11;k++)
                        for(int q = 1;q<=p;q++){ //判断是否有重复
                            if(USER_PRIVILEGES[i][q] == ROLES[j][k]){
                                if(Integer.valueOf(USER_LEVEL[i][q]) <Integer.valueOf(CATEGOPYS_LEVEL[j][k])){
                                    USER_LEVEL[i][q] = CATEGOPYS_LEVEL[j][k];
                                }
                            }else{
                                USER_PRIVILEGES[i][p] = ROLES[j][k];
                                USER_LEVEL[i][p] = CATEGOPYS_LEVEL[j][k];
                                p++;
                            }
                        }

                     //   k++;
               //     }
                }
               j++;
            }
*/

            }
        }



    private static String Query_r(String user,String privilege){

System.out.println("---开始查询：user="+user);
      // System.out.println(USERS[0][0]);
        for(int i = 0; i<USER_NUM;i++ )
            if(USERS[i][0].equals(user)) { //查到用户
                System.out.println("查到用户:"+user);
                String[] temp = privilege.split(":");
                for (int j = 0; j < 10; j++){
                    int k = 0;
                    while(ROLES[k][0]!=null){
                        if (USERS[i][j + 1] .equals( ROLES[k][0])) {  //如果用户角色 == 角色名
                            System.out.println("用户角色 == 角色名角色名为"+ROLES[k][0]);
                            for (int p = 0; p < 10; p++)
                                if (ROLES[k][p + 1] .equals(temp[0]) ) {
                                    System.out.println("查到权限为："+temp[0]);
                                    if (temp.length == 2) {  //用户输入带权限等级的情况

                                        if (Integer.valueOf(CATEGOPYS_LEVEL[k][p + 1]) >= Integer.valueOf(temp[1])) {
                                            System.out.println("查到用户输入带权限等级的情况最大等级为："+CATEGOPYS_LEVEL[k][p + 1]);
                                            return "true";
                                        } else {
                                            return "false";
                                        }
                                    } else {
                                        if (CATEGOPYS_LEVEL[k][p + 1].equals( "-1")) {
                                            System.out.println("查到用户输入不带等级权限");
                                            return "true";
                                        } else {
                                            System.out.println("查到用户未输入等级权限，等级为："+ CATEGOPYS_LEVEL[k][p + 1]);
                                            return CATEGOPYS_LEVEL[k][p + 1];
                                        }

                                    }

                                }
                        }

                        k++;
                    }
                }

                }

        return "false";
    }



    public static void main(String[] args){
   //     Main  m = new Main();
        Scanner sc = new Scanner(System.in);

        int p = sc.nextInt(); //category
        for(int i = 0; i < p; i++)
            CATEGORYS.add(sc.next());

        int r = sc.nextInt(); //role
        for(int i = 0; i < r ; i++) {
            String role =  sc.next();
            ROLES[i][0] = role;
            CATEGOPYS_LEVEL[i][0] =  role;
            int t = 0;  //权限个数
            t = sc.nextInt();
            for(int j = 0; j < t; j++) {  //权限
                String priv = sc.next();
                String temp[] =  priv.split(":");
                String level = "-1";

                if(temp.length==2){    //如果分等级权限；
                    priv = temp[0];  //设置权限名称
                    level = temp[1]; //设置权限等级
                }

                //判断权限是否重复，如果重复，比较等级大小，（因为没有权限等级的为-1，所以没有影响）
                boolean bl = true;
                for(int k = 0; k < j+1; k++){
                    if((priv == ROLES[i][k+1])) { //第一位为role，所以从第二位开始对比
                        bl = false;
                       if( Integer.valueOf(CATEGOPYS_LEVEL[i][k+1]) < Integer.valueOf(level) ){
                           CATEGOPYS_LEVEL[i][k+1] = level; //更新权限最大等级
                       }
                    }
                }
                if(true){
                    ROLES[i][j + 1] = priv;
                    CATEGOPYS_LEVEL[i][j+1] = level;  //插入等级
                }
            }
        }

        USER_NUM = sc.nextInt(); //user
        for(int i = 0; i < USER_NUM; i++){
            USERS[i][0] = sc.next();
            int t = 0;
            t = sc.nextInt();
            for(int j = 0;j < t; j++)
                USERS[i][j+1] = sc.next();
        }
        userToPrivilege();
        System.out.println("---USERS_P---");
        for(int i = 0; i < USER_NUM; i++){
            int j = 0;
            while(USER_PRIVILEGES[i][j]!=null){
                System.out.print(USER_PRIVILEGES[i][j]+" ");
            }
            System.out.println();
        }
        System.out.println("---END USERS_P---");

/*
        System.out.println("---CATEGORYS---");
        for(int i =0 ;i<CATEGORYS.size();i++)
            System.out.print(CATEGORYS.indexOf(i)+" ");
        System.out.println("---END CATEGORYS---");

        System.out.println("---ROLES---");
        int i = 0;
        while(ROLES[i][0]!= null) {
            for (int j = 0; j < 11; j++)
                System.out.print(ROLES[i][j]+" ");
            System.out.println();
            i++;
        }
        System.out.println("---END ROLES---");


        System.out.println("---CATEGOPYS_LEVEL---");
        i = 0;
        while(CATEGOPYS_LEVEL[i][0]!= null) {
            for (int j = 0; j < 11; j++)
                System.out.print(CATEGOPYS_LEVEL[i][j]+" ");
            System.out.println();
            i++;
        }
        System.out.println("---END CATEGOPYS_LEVEL---");

        System.out.println("---USERS---");
        i = 0;
        while(USERS[i][0]!= null) {
            for (int j = 0; j < 11; j++)
                System.out.print(USERS[i][j]+" ");
            System.out.println();
            i++;
        }
        System.out.println("---END USERS---");
*/
        //接下来一行是一个正整数 q，表示权限查询的数量。紧接着的 q 行被称为 Q 段，每行描述一个授权查询，格式为 <user> <privilege>，
 /*       int q = sc.nextInt(); // query

        String[][] User_Query = new String [q][2];

        for(int i = 0 ; i < q; i++){
            User_Query[i][0] = sc.next();
            User_Query[i][1] = sc.next();
        }

        for(int i = 0 ; i < q; i++)
            System.out.println( Query_r(User_Query[i][0],User_Query[i][1]));

*/

    }
}
