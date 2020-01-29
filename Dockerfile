FROM clojure:tools-deps AS base
RUN git clone https://github.com/torgeir/cljs-workshop.git /cljs-workshop
WORKDIR /cljs-workshop
RUN clojure -m figwheel.main --build-once dev

FROM clojure:tools-deps
COPY --from=base /root/.m2 .m2
COPY --from=base /cljs-workshop /cljs-workshop
WORKDIR /cljs-workshop
CMD ["clojure", "-m", "figwheel.main", "-b", "dev"]