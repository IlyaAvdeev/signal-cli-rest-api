#!/bin/sh

sudo docker run -d --restart=always -p 8080:8080 -v /home/llya/.local/share/signal-cli:/home/.local/share/signal-cli \
      -e 'MODE=native' inoi/signal-cli-rest-api:0.11.6