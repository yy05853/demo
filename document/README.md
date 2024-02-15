# 環境構築手順

学習成果として、私が実施した環境構築の手順概要を記載します。

## 技術要素

- フロントエンド
  - 言語
    - TypeScript
  - フレームワーク
    - Vue.js
  - テスティングフレームワーク
    - 未定
- バックエンド
  - 言語
    - Java 17
  - フレームワーク
    - Spring Boot 3.2.1
  - テスティングフレームワーク
    - JUnit
  - ビルドツール
    - Gradle 8.5
- データ管理
  - データベース管理ツール
    - MariaDB
  - データベース移行ツール
    - Flyway
- 開発環境
  - OS
    - Debian 11.8
  - コンテナ
    - Docker
    - Docker Compose 2.2.3
- 本番環境
  - 仮想サーバ
    - Amazon EC2
  - OS
    - 未定
  - AWS権限管理
    - AWS IAM
- 開発支援
  - プログラム及びテキスト作成
    - VSCode
  - プログラミング支援
    - GitHub Copilot
  - バージョン管理
    - Git 2.32.0
    - GitHub
  - 構成管理
    - Ansible(検討中)
  - タスク管理
    - GitHub
  - ドキュメント管理
    - GitHub
    - Notion
  - チャットツール
    - Slack
  - 総合支援
    - ChatGPT 4
- 作業環境
  - マシン
    - Chromebook
  - OS
    - ChromeOS 120.0.6099.203

## 手順

### Chromebook に作業環境を構築する

- ChromebookでLinuxを有効化する
  - [Chromebook で Linux をセットアップする](https://support.google.com/chromebook/answer/9145439?hl=ja)
- マシンにVSCodeをインストールする
  - [ChromeBook で VisualStudioCode を使う](https://zenn.dev/gatabutsu/articles/82008b901c4f04)
- Javaを導入する
  - [ChromeBookでJavaを使う](https://gotoblog.org/chromebook-java/)
- Gitを最新版に更新する
  - [ChromeOSでGit, VSCode, Dockerをインストールして開発 - Gitを最新版に更新](https://qiita.com/pyama2000/items/90b189964f71def53b19#git%E3%82%92%E6%9C%80%E6%96%B0%E7%89%88%E3%81%AB%E6%9B%B4%E6%96%B0)
- GitHubに登録する
  - [GitHub でのアカウントの作成](https://docs.github.com/ja/get-started/quickstart/creating-an-account-on-github)
- GitHub Copilot を導入する
  - [GitHub Copilot の概要](https://docs.github.com/ja/copilot/using-github-copilot/getting-started-with-github-copilot)
  - [VSCode ではじめる GitHub Copilot 活用術](https://qiita.com/RyoWakabayashi/items/1207128e88669c76bf5f)

### Spring Boot のプロジェクトを作成する

- ブラウザ版の Spring Initializr を利用してテンプレートをダウンロードする
  - 下記の設定値を入力し、「GENERATE」ボタンをクリックする
    - Project
      - Gradle - Groovy
    - Language
      - Java
    - Spring Boot
      - 3.2.1
    - Project Metadata
      - 適当に
    - Packaging
      - Jar
    - Java
      - 17
    - Dependencies
      - Spring Web
      - Lombok
- ダウンロードしたテンプレートを解凍ツールで任意のディレクトリに展開する
- 展開したディレクトリを VSCode で開く
- DemoApplication.java を VSCode 上で実行する
- 作業マシン上のブラウザで http://localhost:8080 にアクセスし、「Whitelabel page」と表示されることを確認する

### MariaDB と接続する

- Docker をインストールする
  - [Docker Engine インストール（Debian 向け）](https://matsuand.github.io/docs.docker.jp.onthefly/engine/install/debian/)
- Docker Compose をインストールする

```bash
sudo curl -L "https://github.com/docker/compose/releases/download/v2.2.3/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
sudo chmod +x /usr/local/bin/docker-compose
docker-compose --version
```

- docker-compose.ymlファイルを利用し、MariaDBを動作させるコンテナを作成する

```yaml
version: '3.1'

services:
  mariadb:
    image: mariadb
    restart: always
    environment:
      MYSQL_ROOT_PASSWORD: my-secret-pw
      MYSQL_DATABASE: mydatabase
      MYSQL_USER: user
      MYSQL_PASSWORD: password
    ports:
      - "3306:3306"
    volumes:
      - mariadb-data:/var/lib/mysql

volumes:
  mariadb-data:
```

※ my-secret-pw 及び password 部分については、MariaDB に設定したパスワードを記載すること。

- コンテナを起動する

```bash
docker-compose up -d
```

- MariaDB にデータを登録する

- 依存を追加
- .env ファイル
- application.properties の記載
- MariaDB からデータを取得する Java コードを作成する
- 動作確認をする

------

- アプリ起動
    - コンテナ実行
        - cd ~/demo/demo/docker/
        - sudo docker-compose up -d
    - Spring Boot立ち上げ
        - VSCode＞Gradle＞demo>tasks>application>bootRun
    - アプリアクセス
        - http://localhost:8080/
- DBアクセス
    - コンテナにログインする
        - sudo docker ps
        - sudo docker exec -it [コンテナID] bash
    - mariadbにログインする
        - sudo docker exec -it [コンテナID] mariadb --host localhost --user root --password --database mydatabase
            - パスワードは.envファイル参照
        - use mydatabase
        - 
        
        CREATE TABLE `users` (
        `id` int(11) NOT NULL AUTO_INCREMENT,
        `name` varchar(255) DEFAULT NULL,
        `email` varchar(255) DEFAULT NULL,
        PRIMARY KEY (`id`)
        ) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci
        
        - insert into users (id, name, email) values (1, 'Alice', 'alice@example.com');
        - show tables;
- AWS
    - AWSアカウント・IAM作成とセキュリティ設定
        - [AWS アカウント作成の流れ(公式)](https://aws.amazon.com/jp/register-flow/)
        - [AWSの始め方](https://zenn.dev/k_tana/articles/2023-05_how-to-start-aws)
        - [個人利用AWSアカウントの作成](https://qiita.com/ti_and6id/items/7aaf34074346c7e5c7cc)
        - [これをやっておけば大丈夫！AWSアカウント作成後にやること！](https://zenn.dev/issy/articles/zenn-aws-account-initial-setting)
    - EC2(インスタンスの作成とSSH接続)
        - Simplexチームの手順書を流用
        - [AWS EC2 に Chromebook で SSH 接続するまで](https://qiita.com/kutinasi_hobby/items/a124cbc9daa4f9c76d91)
- JDK
    - [Install Java JDK 21 or OpenJDK 21 on Debian 12/11/10](https://computingforgeeks.com/install-java-jdk-or-openjdk-21-on-debian/)
- MariaDB
    - [MariaDB Package Repository Setup and Usage(公式)](https://mariadb.com/kb/en/mariadb-package-repository-setup-and-usage/#-mariadb-server-version)
    - [MariaDBの初期パスワード変更方法](https://qiita.com/heatflat1021/items/c2b2818bdb0d4530f25a)
    - mariadb --host localhost --user root --password
    - CREATE DATABASE mydatabase;
    - use mydatabase
    - show tables;
- httpd
    - sudo apt -y install apache2
    - sudo systemctl status apache2
    - [Apache2 : インストール](https://www.server-world.info/query?os=Debian_11&p=httpd&f=1)
- jarファイル作成
    - [gradleプロジェクトでjarファイルを作成する](https://nbaboston.net/1401.html)
- jar配置ディレクトリ作成
    - sudo mkdir /usr/local/demo
- jarファイル格納
    - scp -i "chrome-key.pem" ~/demo/demo/build/libs/demo-0.0.1-SNAPSHOT.jar [admin@54.95.117.180](mailto:admin@54.95.117.180):
    - ssh -i "chrome-key.pem" [admin@54.95.117.180](mailto:admin@54.95.117.180)
    - sudo cp -p demo-0.0.1-SNAPSHOT.jar /usr/local/demo
- apprunnerとappuserの作成
    - sudo groupadd -g 9000 apprunner
    - sudo useradd -N -g apprunner -s /sbin/nologin appuser
- logフォルダ作成
    - sudo mkdir /var/log/demo
    - sudo chown -R appuser:apprunner /var/log/demo
- SystemCTLの準備
    - sudo vi /etc/systemd/system/demo.service
- application.propertiesの準備
    - sudo vi /usr/local/demo/application-staging.properties
- 起動
    - sudo systemctl start demo.service
    - sudo tail -f /var/log/demo/demo.log
    - sudo systemctl status demo.service
- 8080ポートを開放
    - [【図解】3ステップでAWS(EC2)の特定ポートを開放する手順](http://kakedashi-xx.com:25214/index.php/2020/12/08/post-1529/#toc3)