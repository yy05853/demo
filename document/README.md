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
