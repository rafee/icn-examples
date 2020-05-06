#!/usr/bin/env python

#-
# Copyright (C) 2011  Oy L M Ericsson Ab, NomadicLab
#
# This program is free software; you can redistribute it and/or modify
# it under the terms of the GNU General Public License version 3 as
# published by the Free Software Foundation.
#
# See LICENSE and COPYING for more details.
#

"""Simple text subscriber example."""

from blackadder.blackadder import *

def _main(argv=[]):
    strategy = DOMAIN_LOCAL # Our default strategy is domain-local
    if len(argv) >= 2:
        strategy = int(argv[1])
    
    ba = Blackadder_Instance(True)
    
    sid  = '\x0a'+(PURSUIT_ID_LEN-2)*'\x00'+'\x0b'
    rid  = '\x0c'+(PURSUIT_ID_LEN-2)*'\x00'+'\x0d'
    
    ba.subscribe_info(rid, sid, strategy, None)
    
    try:
        while True:
            ev = Event()
            ba.getEvent(ev)
            print ev
            if ev and ev.type == PUBLISHED_DATA:
                print "id=%r" % ev.id
                print ev.data_len, "%r" % (ev.data[:ev.data_len] if ev.data
                                           else ev.data)
    finally:
        ba.disconnect()

if __name__ == "__main__":
    import os;  print "PID =", os.getpid()
    import sys
    _main(sys.argv)
