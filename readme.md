# Generative art playground

A ClojureScript generative art playground, with a repl and hot reloading support.

## Install

- Install [java 8](https://adoptopenjdk.net/)

### OS X
Installer homebrew
```
/usr/bin/ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"
```

```sh
brew update
brew install clojure
```

### Linux

Don't use `apt install clojure`, it somehow messes up the figwheel initialization process later.

Make sure you have the following dependencies installed `bash`, `curl` and `rlwrap`.

Run the following:

```sh
curl -O https://download.clojure.org/install/linux-install-1.10.1.447.sh
chmod +x linux-install-1.10.1.447.sh
sudo ./linux-install-1.10.1.447.sh
```

It creates `/usr/local/bin/clj`, `/usr/local/bin/clojure`, and `/usr/local/lib/clojure`.

Run it with `./linux-install-1.10.1.447.sh --prefix somewhere/else/` if you want the files installed to `somewhere/else/` instead.

### Windows

Open PowerShell and run the following:

```ps
Invoke-RestMethod -Uri https://download.clojure.org/install/win-install-1.10.1.469.ps1 -OutFile installer.ps1
./installer.ps1
```

If you encounter the following error when running the installer:
```sh
path\to\file\win-install-1.10.1.469.ps1 cannot be loaded. The file path\to\file\win-install-1.10.1.469.ps1 is not digitally signed.
```

...try running the following:
```ps
Set-ExecutionPolicy -Scope Process -ExecutionPolicy Bypass
```

...and try again.


## Run it, with a REPL on steriods inside your favourite editor

### VSCode

Install `Calva: Clojure & Clojurescript Interactive Programming`

Open `src/sketches/init.cljs`.

Hit `cmd + shift + p` and start type `jack` and hit enter.

Select `Clojure CLI + Figwheel Main`, hit enter, wait, select `dev`, hit enter.

Evaluate something in the browser from your editor with `ctrl+alt+c e`.

### Emacs

Install `cider`.

Run `cider-jack-in-cljs`

## Run it, without a REPL and editor integration

### Terminal

Run `clojure -m figwheel.main -b dev` from the project root.

## Resource

- Cheat sheet
https://cljs.info/cheatsheet/

- Clojure quick start
https://clojurescript.org/guides/quick-start

- Color palettes
https://kgolid.github.io/chromotome-site/

- Quil API http://quil.info/api
