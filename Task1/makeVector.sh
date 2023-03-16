#! /bin/sh
wget https://github.com/dwslab/jRDF2Vec/raw/jars/jars/jrdf2vec-1.3-SNAPSHOT.jar
wget https://dlcdn.apache.org/jena/binaries/apache-jena-4.7.0.tar.gz
tar xf apache-jena-4.7.0.tar.gz
apache-jena-4.7.0/bin/riot  --out=NT $HOME/KGRC-RDF/RDF/*.ttl > total.nt
java -jar jrdf2vec-1.3-SNAPSHOT.jar -graph total.nt
rm -rf data
mkdir data
egrep instance/event walks/vectors.txt > data/events-vectors.txt
sed 's/^[^ ]* //' data/events-vectors.txt > data/matrix.txt
awk -F ' ' '{ print $1 }' data/events-vectors.txt > data/events.txt
