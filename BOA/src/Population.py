from Butterfly import Butterfly

class Population:

    def __init__(self, size, dim, fro, to, fun, p=0.8, c=0.01, a=0.1) -> None:
        self._butters = []
        for i in range(size):
            self._butters.append(Butterfly(dim, fro, to, fun, p, c, a))
        self._set_best_particle()

    def _set_best_particle(self):
        self._butters.sort()
        self._best_particle = self._butters[0]

    def _cal_fregrance(self):
        for i in self._butters:
            i._cal_fragrance()
        self._set_best_particle()