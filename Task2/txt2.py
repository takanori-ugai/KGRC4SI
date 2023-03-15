import txt

filepath = "./a.txt"
with open(filepath, "r", encoding="utf-8") as f:
    lines = f.readlines()
    for line in lines :
        print(txt.makeTitle(line))
        print(txt.printScript(line))
