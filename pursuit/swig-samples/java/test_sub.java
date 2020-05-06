/*-
 * Copyright (C) 2011  Oy L M Ericsson Ab, NomadicLab
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 3 as
 * published by the Free Software Foundation.
 *
 * See LICENSE and COPYING for more details.
 */

/* Simple text subscriber example. */

class Subscriber {
    static {
        System.loadLibrary("blackadder_java");
    }
    static final blackadder_java.BA BA = null;

    int strategy = BA.DOMAIN_LOCAL;

    void subscribe(byte[] sid, byte[] rid) {
        blackadder_java.Blackadder ba =
            blackadder_java.Blackadder.Instance(true);

        try {
            ba.subscribe_info(rid, sid, strategy, null);

            while (true) {
                blackadder_java.Event ev = new blackadder_java.Event();
                ba.getEvent(ev);
                int type = ev.getType();
                System.out.println(type);
                if (type == 0)
                    return;
                if (type == BA.PUBLISHED_DATA) {
                    byte[] data = ev.getData();
                    System.out.println(ev.getData_len());
                    System.out.println(new String(data));
                }
            }
        }
        finally {
            ba.disconnect();
        }
    }
}

public class test_sub {
    public static void main(String args[]) throws Exception {
        byte[] sid = new byte[8]; sid[0] = 0x0a; sid[7] = 0x0b;
        byte[] rid = { 0x0c, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x0d };

        //byte[] id = new byte[sid.length + rid.length];
        //System.arraycopy(sid, 0, id, 0, sid.length);
        //System.arraycopy(rid, 0, id, sid.length, rid.length);

        Subscriber sub = new Subscriber();
        sub.subscribe(sid, rid);
    }
}
