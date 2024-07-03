from pso.Swarm import Swarm
import random
from pso.Particle import Particle
import numpy as np
from pso.Example import Example

class GLPSO:

    def __init__(self, fun):
        self.iter = 400
        self.w =[ float(i) for i in np.arange(0.4,0.9,0.5/self.iter)]
        self.c1 = [ float(i) for i in np.arange(0.5,2,-1.5/self.iter)]
        self.c2 = [ float(i) for i in np.arange(0.5,2.0,1.5/self.iter)]
        self.fun = fun
        self.fits = []
        self._swarm = None
        self._examplers = []
        self._childs = []
        self.data = []
        self._best_ex = None

    def __pso(self, fro, to, dim):
        self._from = fro
        self._to = to
        self._swarm = Swarm(self.fun, fro, to, dim)
        self._dim = dim
        for i in range(self.iter):
            self._swarm._update_position(self.fun, self.w[i], self.c1[i], self.c2[i])
            print("Wartość optymalna: ", self._swarm._best_particle._best_fit)

            self.fits.append(self._swarm._best_particle._best_fit)
        return self._swarm._best_particle._best_fit
    
    def _run(self, fro, to, dim):
        res = []
        for i in range(30):
            res.append(self.__pso(fro, to, dim))
        return res
    
    def _init_gl(self, fro, to, dim):
        self._dim = dim
        self._from = fro
        self._to = to
        self._swarm = Swarm(self.fun, fro, to, dim)
        self._examplers = []
        for i in range(len(self._swarm._swarm)):
            x = []
            for i in range(dim):
                x.append(np.random.normal())
            self._examplers.append(Example(x))
        self._best_ex = self._swarm._best_particle._fit


    def __gl(self, fro, to, dim ):
        self._init_gl(fro, to, dim)
        tmp = []

        for k in range(self.iter):
            self._childs= []
            self._swarm._update_bests(self.fun)
            for i in range(self._swarm._swarm_size):
                self.__crossover(self._swarm._swarm[i])
                self.__mutation(self._childs[i])
                self.__selection(i)
                if self._examplers[i]._stop >= 7:
                    self._examplers[i]._stop = 0
                    self.__tournament(i) 

                self._swarm._update_position(self.fun, 0.7, 1.5, self._examplers)
                if self._swarm._swarm[i]._best_fit < self._best_ex:
                    self._best_ex = self._swarm._swarm[i]._best_fit

            tmp.append(self._swarm._best_particle._fit)
        self.data.append(tmp)

        return self._swarm._best_particle

    def _glpso(self, fro, to, dim):
        res = []
        for i in range(20):
            best = self.__gl(fro, to, dim)
            res.append(best._fit)
        print("Wartość optymalna: ", min(res))
        return res


    def __crossover(self, part):
        particle = self._swarm._swarm[random.randint(0, self._swarm._swarm_size - 1)]
        new = []
        for i in range(self._dim):
            if part._best_fit < particle._best_fit:
                r = random.random()
                new.append(r * part._best_x[i] + (1 - r) * self._swarm._best_particle._best_x[i])
            else:
                new.append(particle._best_x[i])

        p = Example(new)
        p._count_fit(self.fun)
        self._childs.append(p)
        return p
    
    def __mutation(self, part):
        for i in range(self._dim):
            if random.random() < 0.01:
                part._x[i] = random.randint(self._from, self._to)
                # part._x[i] = np.random.normal()

        part._count_fit(self.fun)
        return part

    def __selection(self, i):
        if self._childs[i]._f < self._examplers[i]._f:
            self._examplers[i] = self._childs[i]
            self._examplers[i]._stop = 0
            if self._examplers[i]._f < self._best_ex:
                self._best_ex = self._examplers[i]._f
        else:
            self._examplers[i]._stop += 1
    
        self._examplers[i]._count_fit(self.fun)

    def __tournament(self, i):
        n = int(0.2 * self._swarm._swarm_size)
        fight = []
        tmp = self._examplers.copy()
        for j in range(n):
            ex = tmp[random.randint(0, len(tmp)-1)]
            fight.append(ex)
            tmp.remove(ex)
        tmp.sort()
        self._examplers[i] = tmp[0]
        

    


