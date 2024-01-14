#!/bin/bash

# .env ファイルのパスを指定
ENV_FILE=".env"

# .env ファイルが存在するか確認
if [ ! -f "$ENV_FILE" ]; then
    echo "エラー: .env ファイルが見つかりません。"
    exit 1
fi

# .env ファイルから環境変数を読み込み、エクスポート
while IFS='=' read -r key value
do
    export "$key=$value"
done < "$ENV_FILE"

echo ".env ファイルから環境変数を読み込みました。"
