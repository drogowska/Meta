
from Population import Population
import numpy as np


class BOA:

    def __init__(self, fro, to, dim, fun, size, p=0.8, c=0.01, a=0.1) -> None:
        self._population = Population(size, dim, fro, to, fun)
        self.iter = 400
        self._c = c
        self._a = a
        self._p = p
        self._from = fro
        self._to = to
        self.fun = fun
        self._dim = dim
        self.size = size
        self.data = []
        
    def __init(self,  fro, to, dim, fun, size):
        self._population = Population(size, dim, fro, to, fun, self._p, self._c, self._a)

    def _run(self):
        res = []
        for i in range(20):
            self.__init(self._from, self._to, self._dim, self.fun, self.size)
            res.append([self._p, self._c, self._a, self._boa()._fragrance])
        print("Wartość optymalna: ", min(res), '\n')

        return res


    def _boa(self):
        tmp = []
        self._population._cal_fregrance()
        for j in range(self.iter):
            for i in self._population._butters:
                id = self._population._butters.index(i)
                i._update_position(self._population._best_particle, self._population)
            self._population._cal_fregrance()
            tmp.append(self._population._best_particle._fragrance)
        self.data.append(tmp)
        return self._population._best_particle



