package tags;

import sys.tags.AuthBean;
import sys.tags.AuthTag;

public class AuthTagTest {

    public static void main(String[] args) throws Exception {


        String res = "/p/123123123-123123123-21312.pdf";
        String encode = AuthTag.sign(res, 122, "user:list", "getUser", null);

        System.out.println("encode = " + encode);

        AuthBean decode = AuthTag.decode(res, encode);

        System.out.println("decode = " + decode);

    }
}
