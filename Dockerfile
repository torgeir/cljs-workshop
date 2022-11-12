FROM clojure:tools-deps AS base
RUN git clone https://github.com/torgeir/cljs-workshop.git /cljs-workshop
WORKDIR /cljs-workshop
RUN clojure -M -m figwheel.main --build-once dev

FROM clojure:tools-deps
COPY --from=base /root/.m2 /root/.m2
COPY --from=base /cljs-workshop /root/cljs-workshop
WORKDIR /root/cljs-workshop
CMD clojure -M -m figwheel.main -b dev
