import random as random
from Functions import SphereFunction
from Functions import SchwefelFunction
from Functions import CigarFunction

class Particle:
    def __init__(self, fro, to, dim, x=None, copy=False):
        if copy:
            self._x = x
            self._fit = 0
            self._best_fit = 0
            self._fit = 0
            self._velo = []
            for i in range(dim):
                self._velo.append(random.uniform(-1, 1))
            self._stop = 0
            self._best_x = x
        else:
            self._best_fit = 0
            self._fit = 0
            self._velo = []
            self._x = []
            for i in range(dim):
                self._x.append(random.uniform(fro, to))
                self._velo.append(random.uniform(-1, 1))
            self._best_x = self._x
            self._done = False
            self._subswarm_no = 0
            self._best_x = self._x
            self._stop = 0


    def _update_velo(self, w, c1, c2, best):  
        for i in range(len(self._velo)):
            self._velo[i] = self._velo[i] * w + random.random() * c1 * (self._best_x[i] - self._x[i]) + random.random() * c2 * (best._x[i] - self._x[i])
    
    def _update_velo_ex(self, w, c1, ex):  
        for i in range(len(self._velo)):
            self._velo[i] = self._velo[i] * w + random.random() * c1 * (ex._x[i] - self._x[i]) 
    
    def _update_runner(self, winner):
        for i in range(len(self._velo)):
            self._velo[i] = random.random() * self._velo[i]  + random.random() * (winner._x[i] -self._x[i])
        self._update_position()          

    def _update_looser(self, winner, runner):
        for i in range(len(self._velo)):
            self._velo[i] = random.random() * self._velo[i]  + random.random() * (winner._x[i] - self._x[i]) + random.random() * (runner._x[i] - self._x[i])
        self._update_position()          

    def _update_cso(self, winner, avg):
        for i in range(len(self._velo)):
            self._velo[i] = random.random() * self._velo[i]  + random.random() * (winner._x[i] - self._x[i]) + random.random() * (avg[i] - self._x[i])
        self._update_position()   

    def _count_fit(self, fun):
        if fun == 0:
            f = SphereFunction()
        elif fun == 1 :
            f = SchwefelFunction()
        else:
            f = CigarFunction()
        self._fit = f._fun(self._x)
    

    def _update_best(self, fun):
        if fun == 0:
            f = SphereFunction()
        elif fun == 1 :
            f = SchwefelFunction()
        else:
            f = CigarFunction()
        self._best_fit = f._fun(self._best_x)
        if self._best_fit > self._fit:
            self._stop = 0
            self._best_x = self._x
            self._best_fit = self._fit
        else :
            self._stop += 1


    def _update_position(self):
        if len(self._velo) == 0:
            return

        for i in range(len(self._x)):
            self._x[i] += self._velo[i]

    def __eq__(self, other): 
        if not isinstance(other, Particle):
            return NotImplemented
        return self._fit == other._fit and self._x == other._x
    
    def __gt__(self, other):
        if not isinstance(other, Particle):
            return NotImplemented
        return self._fit > other._fit
    
