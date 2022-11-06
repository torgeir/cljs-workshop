# Generative art playground

A ClojureScript generative art playground, with a repl and hot reloading support.


## Install

### colima (docker)

- Install packages for docker using your favorite macOS package manager
- Install colima to use as container runtime

Start colima

```
colima start
```

Run the workshop

```
docker run -it -p9500:9500 torgeir/cljs-workshop
```

It will say it can't open the browser, but that's ok, do it yourself:

Open [localhost:9500](http://localhost:9500) in the browser.

To be able to edit files inside the docker container:

- Click the Extensions tab in vscode
- Install the `Remote - Containers` extension and the `Calva: Clojure & Clojurescript Interactive Programming` extension
- A new icon `Remote Explorer` on the left shows running docker containers
- Right click on the container `torgeir/cljs-workshop` and choose `Connect to container`
- When the new window opens, click `Open folder` and enter `/root/cljs-workshop`

You are ready to edit sketches!

### Other ways

- Install [java 8](https://adoptopenjdk.net/)

#### OS X
Installer homebrew

```
/usr/bin/ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"
```

```sh
brew update
brew install clojure
```

#### Linux

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

#### Windows

Open PowerShell as administrator (right click it an choose "run as administrator") and run the following:

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

### Run it manually

`clojure -M -m figwheel.main -b dev`

### Run it, with a REPL on steriods inside your favourite editor

(when not using the docker approach)

#### VSCode

Install `Calva: Clojure & Clojurescript Interactive Programming`

Open `src/sketches/init.cljs`.

Hit `cmd + shift + p` and start type `jack` and hit enter.

Select `deps.edn + Figwheel Main`, hit enter, wait, select `dev`, hit enter.

Evaluate something in the browser from your editor with `ctrl+alt+c e`.

#### Emacs

Install `cider`.

Run `cider-jack-in-cljs`

## Resources

- Cheat sheet
https://cljs.info/cheatsheet/

- Clojure quick start
https://clojurescript.org/guides/quick-start

- Color palettes
https://kgolid.github.io/chromotome-site/

- Quil API http://quil.info/api
