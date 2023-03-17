# KGRC4SI
## Task1の実行方法
```
cd Task1
./gradlew run
cd ../QA
python QA.py
```

### 動作確認環境
- OS: linux (with GPU)
- Python 3.10
- JDK17
### 新しいデータを学習、評価に用いる方法
- makeVectors.sh のデータの置き場 ($HOME/KGRC-RDF/RDF/*.ttl)を置き換える
- makeVectors.sh を実行
- data 以下に生成されるデータを次の場所に置く data/events.txt -> node.txt data/matrix.txt -> matrix.csv
- cd Task1
- src/Main.kt の正解ラベルを書き換える
- ./gradlew run を実行

## Task2の実行方法
```
sh makeTrain.sh
python makeScripts.py
```
scripts の下に台本データが作成される

### 動作確認環境
- OS: linux (GPU MEM 8G)
- Python 3.10
### 準備
- pip install happytransformer
### 新しいデータを学習に用いる方法
- makeTrain.sh の実行後に KGRC-RDF/Program の下に台本データを置く
- python KGRC-ws-2022/PtoSentence.py > train.txt を実行する
- python makeScripts.py の実行により新しい台本データが作成される
### より大きなモデルを用いる場合 (GPUにメモリが必要)
- makeScripts.py の happy_gen = HappyGeneration("GPT2", "gpt2") で用いるモデルを置き換える
