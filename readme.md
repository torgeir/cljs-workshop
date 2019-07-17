# Generative art playground, using ClojureScript with repl and hot reloading support

## Install

- Install [java 8](https://adoptopenjdk.net/)

### OS X

```sh
brew update
brew install clojure
```

### Linux

Don't use `apt install clojure`, it somehow messes up the figwheel initialization process later.

Make sure you have the following dependencies installed `bash`, `curl` and `rlwrap`.

Run the following

```sh
curl -O https://download.clojure.org/install/linux-install-1.10.1.447.sh
chmod +x linux-install-1.10.1.447.sh
sudo ./linux-install-1.10.1.447.sh
```

It creates `/usr/local/bin/clj`, `/usr/local/bin/clojure`, and `/usr/local/lib/clojure`.

### Windows

Use the provided `.\clj.exe` as a replacement for the `clj` command.

## REPL on steroids

### VSCode

Install `Calva: Clojure & Clojurescript Interactive Programming`

Hit `cmd + shift + p` and start type `jack` and hit enter.

Select `Clojure CLI + Figwheel Main`, select `:repl`, wait, select `dev`.

Evaluate something in the browser from your editor with `ctrl+alt+c e`.

### Emacs

Install `cider`.

Run `cider-jack-in-cljs`.

### Manually
```
clj -Arepl
```

## Resource

https://cljs.info/cheatsheet/

https://clojurescript.org/guides/quick-start
