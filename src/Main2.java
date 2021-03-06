
import java.util.*;

/**
 * Created by admin on 2017/3/11.
 */
public class Main2 {

    private static List<String> CATEGORYS = new ArrayList<>();

    private static HashMap<String,HashMap<String,String>> ROLES = new HashMap<>();  //role-priv

    private static HashMap<String,List<String>> USERS = new HashMap<>(); //user-role

    private static HashMap<String,HashMap<String,String>> USER_PRIV = new HashMap<>(); //user - priv



    private static void userSetPriv(){
        Iterator iter = USERS.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String key = entry.getKey().toString();
            List<String> val = (List<String>) entry.getValue();

            HashMap<String,String> privHashMap = new HashMap<>();

            Iterator<String> iter2  = val.iterator();
            while(iter2.hasNext()){ //每个USER的role迭代
                HashMap<String,String> tempHashMap = ROLES.get(iter2.next()); //得到每个role的priv
                Iterator iter_ROLE = tempHashMap.entrySet().iterator(); //迭代出role里的权限
                while (iter_ROLE.hasNext()) {
                    Map.Entry<String,String> entry_PRIV = (Map.Entry) iter_ROLE.next();
                    if(privHashMap.containsKey(entry_PRIV.getKey())) {  //如果权限存在
                        if (Integer.valueOf(privHashMap.get(entry_PRIV.getKey())) < Integer.valueOf(entry_PRIV.getValue())) {  //如果权限等级大，就覆盖
                            privHashMap.put(entry_PRIV.getKey(), entry_PRIV.getValue());
                        }
                    }else{  //如果权限不存在，就插入
                        privHashMap.put(entry_PRIV.getKey(),entry_PRIV.getValue());
                    }

                }


            }
            USER_PRIV.put(key,privHashMap);
        }

    }

    private static String query_Priv(String user,String privilege){

        HashMap<String,String> privHashMap = USER_PRIV.get(user);
        if(privHashMap!=null){ //用户存在
            String temp[] =  privilege.split(":");
            String level =  privHashMap.get(temp[0]);
            if(level != null){
                if(temp.length == 2){
                     if(Integer.valueOf(level)>= Integer.parseInt(temp[1])){
                         return "true";
                     }else{
                         return "false";
                     }
                } else{
                    if(level.equals("-1"))
                        return "true";
                    else
                        return level;

                }
            }else{
                return "false"; //如果权限不存在
            }


        }else{
            return "false";
        }

    }
    public static void main(String[] args){

        Scanner sc = new Scanner(System.in);

        int p = sc.nextInt(); //category
        for(int i = 0; i < p; i++)
            CATEGORYS.add(sc.next());

        int r = sc.nextInt(); //role
        for(int i = 0; i < r ; i++) {
            String key = sc.next(); //role
            int s = sc.nextInt();

            HashMap<String,String> hashMap = new HashMap<>();
            for(int j = 0 ; j < s ;j++){
                String priv = sc.next();
                String temp[] =  priv.split(":");
                if(temp.length == 2){
                    if(hashMap.containsKey(temp[0])){ //判断key是否已经存在，如果存在比较大小
                        if(Integer.valueOf(hashMap.get(temp[0]))<Integer.valueOf(temp[1])){
                            hashMap.put(temp[0],temp[1]);
                        }
                    }else{
                        hashMap.put(temp[0],temp[1]);
                    }
                }else{
                    hashMap.put(temp[0],"-1");
                }
            }
            ROLES.put(key,hashMap);
        }

        int u = sc.nextInt(); //user
        for(int i = 0; i < u; i++){
            String key = sc.next();
            int t = sc.nextInt();

            List<String> list = new ArrayList<>();
            for(int j = 0 ; j < t ; j++)
                list.add(sc.next());
            USERS.put(key,list);
        }


        String [][] UserQuery = new String[10000][2];
        int q  = sc.nextInt();
        for(int i = 0 ; i < q; i++){
            String user = sc.next();
            String priv = sc.next();
            UserQuery[i][0] = user;
            UserQuery[i][1] = priv;
        }

        userSetPriv();

        for(int i = 0 ; i < q; i++){
            System.out.println(query_Priv(UserQuery[i][0],UserQuery[i][1]));
        }


/*        System.out.println(USER_PRIV.size());
        Iterator iter = USER_PRIV.entrySet().iterator();
        while(iter.hasNext()){
            Map.Entry entry = (Map.Entry) iter.next();
            String user = entry.getKey().toString();
            System.out.println(user);
        }*/


    }
}
