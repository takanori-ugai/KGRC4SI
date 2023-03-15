import re
import glob
import json

with open('objects.txt', "r", encoding="utf-8") as objects:
    olist = objects.read().splitlines()
#    print(olist)
with open('./replaceback.json') as f:
    df = json.load(f)

def printScript(line) :
        sentenses = re.split(',|\.', line)
        sentense = "walks to" + sentenses[0]
        ret = sentense + "\n\n"
        actions = re.split('and ', sentense)
#        print(actions)
        for action in actions :
            ac = re.split(' ', action)
#            print(ac)
#            print(ac[0] + ac[1])
            if len(ac) >1 and ac[0] + " " + ac[1] in df.keys() :
                for target in ac :
                    if(target in olist):
                        if ac[0] != "walks" and ac[0] != "finds" :
                            ret += "[walk]" + " <" + target +">\n"
                            ret += "[find]" + " <" + target +">\n"
                        ret += "[" + df[ac[0]+" " + ac[1]] + "]" + " <" + target +">\n"
            if len(ac) >0 and ac[0] in df.keys() :
                for target in ac :
                    if(target in olist):
                        ret += "[walk]" + " <" + target +">\n"
                        ret += "[find]" + " <" + target +">\n"
                        ret += "[" + df[ac[0]] + "]" + " <" + target +">\n"
        return ret

def makeTitle(line) :
    sentenses = re.split(',|\.', line)
    return "In the morning Mr. Avator walks to" + sentenses[0] + " and done. :"
