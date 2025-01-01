#!/bin/sh

CONFIG_FILE="/usr/share/nginx/html/assets/config.json"
CONFIG_DIR="/usr/share/nginx/html/assets"

# Create assets directory if it doesn't exist
mkdir -p $CONFIG_DIR

echo "Generating runtime configuration..."
cat <<EOF > $CONFIG_FILE
{
  "apiUrl": "${API_URL}"
}
EOF

echo "Configuration file updated at $CONFIG_FILE"
exec "$@"