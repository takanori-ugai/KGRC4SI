from happytransformer import HappyGeneration, GENSettings
import re
import txt
#--------------------------------------#
#happy_gen = HappyGeneration("GPT2", "gpt2-medium")
#happy_gen = HappyGeneration("GPT2", "gpt2-xl") # for best performance
happy_gen = HappyGeneration("GPT2", "gpt2")
args = GENSettings(max_length=30, temperature=0.7)
result = happy_gen.generate_text("Mr. Avator walks to", args=args)    
resultText = result.text
#print(resultText)
#print(txt.makeTitle(resultText))
title = txt.makeTitle(resultText)
result = happy_gen.generate_text(title, args=args)
resultText2 = result.text
print(resultText2)
print(txt.printScript(resultText))

happy_gen.train("train.txt")
for i in range(100) :
    result = happy_gen.generate_text("In morning Mr. Avator walks to", args=args)
    resultText = result.text
#    print(resultText)
    title = txt.makeTitle(resultText)
    result = happy_gen.generate_text(title, args=args)
    resultText2 = result.text
    sentenses = re.split(',|\.|\n', resultText2)
    retText = sentenses[0]
    retText += "\n"
    retText += txt.printScript(resultText)
    f = open("scripts/" + str(i) + ".txt", 'w')
    f.write(retText)
    f.close()
