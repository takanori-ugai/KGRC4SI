# KGRC4SI
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
