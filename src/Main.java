import java.util.*;

public class Main {

    private static List<String> CATEGORYS = new ArrayList<>();

    private static HashMap<String,HashMap<String,String>> ROLES = new HashMap<>();

    private static HashMap<String,List<String>> USERS = new HashMap<>();

    private static HashMap<String,HashMap<String,String>> USER_PRIV = new HashMap<>();



    private static void userSetPriv(){
        Iterator iter = USERS.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String key = entry.getKey().toString();
            List<String> val = (List<String>) entry.getValue();

            HashMap<String,String> privHashMap = new HashMap<>();

            Iterator<String> iter2  = val.iterator();
            while(iter2.hasNext()){
                HashMap<String,String> tempHashMap = ROLES.get(iter2.next());
                Iterator iter_ROLE = tempHashMap.entrySet().iterator();
                while (iter_ROLE.hasNext()) {
                    Map.Entry<String,String> entry_PRIV = (Map.Entry) iter_ROLE.next();
                    if(privHashMap.containsKey(entry_PRIV.getKey())) {
                        if (Integer.valueOf(privHashMap.get(entry_PRIV.getKey())) < Integer.valueOf(entry_PRIV.getValue())) {
                            privHashMap.put(entry_PRIV.getKey(), entry_PRIV.getValue());
                        }
                    }else{
                        privHashMap.put(entry_PRIV.getKey(),entry_PRIV.getValue());
                    }

                }


            }
            USER_PRIV.put(key,privHashMap);
        }

    }

    private static String query_Priv(String user,String privilege){

        HashMap<String,String> privHashMap = USER_PRIV.get(user);
        if(privHashMap!=null){
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
                return "false";
            }


        }else{
            return "false";
        }

    }
    public static void main(String[] args){

        Scanner sc = new Scanner(System.in);

        int p = sc.nextInt();
        for(int i = 0; i < p; i++)
            CATEGORYS.add(sc.next());

        int r = sc.nextInt();
        for(int i = 0; i < r ; i++) {
            String key = sc.next();
            int s = sc.nextInt();

            HashMap<String,String> hashMap = new HashMap<>();
            for(int j = 0 ; j < s ;j++){
                String priv = sc.next();
                String temp[] =  priv.split(":");
                if(temp.length == 2){
                    if(hashMap.containsKey(temp[0])){
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

        int u = sc.nextInt();
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

    }
}
