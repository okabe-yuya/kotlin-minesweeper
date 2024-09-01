CUI-MineSweeper
===

## About

[Exercise: Minesweeper in 100 lines of clean Ruby](https://radanskoric.com/experiments/minesweeper-100-lines-of-clean-ruby)を参考にRubyで実装されていたものをKotlinで実装した。  

CUI上で実行されるシンプルなMineSweeperである。  


## Usage

以下のコマンドを実行するとアプリケーションが立ち上がる。  
`build`しておけば`.jar`形式にしたり、スクリプトファイルとして実行することも可能だと思われる。  
(最終的にはネイティブコンパイルしてバリナリファイルを出力して`ls`コマンドのように`ms`コマンドとしたい)  

```sh
./gradlew run
```

ファイルを実行すると難易度の選択を求められる。  
現在、対応しているのはeasy, normal, hardの3モードで、盤面のサイズと爆弾の数が増えていく。  

**実行例**

```sh
-:::::::::::::::::::::::::::::::::::::::::::-
-:::::Welcome to terminal MineSweeper💣:::::-
-:::::::::::::::::::::::::::::::::::::::::::-

🔽 Select difficulty as 'easy(e), normal(n), hard(h) (default: normal(n))
e

▪▪▪▪▪
▪▪▪▪▪
▪▪▪▪▪
▪▪▪▪▪
▪▪▪▪▪

🔽 Type click coordinate as 'y, x' (1 based)>
1,1

▫▫▫▫▫
11▫▫▫
▪2121
▪▪▪▪▪
▪▪▪▪▪
```

