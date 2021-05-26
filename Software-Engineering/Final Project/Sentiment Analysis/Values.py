
class Values:
    def __init__(self, c, neg, neu, pos, url):
        self.compound = c
        self.negative = neg
        self.neutral = neu
        self.positive = pos
        self.url = url


    def __str__(self):
        return "Compound: " + str(self.compound) + " Neg: " + str(self.negative) +  " Neu: " + str(self.neutral) + " Pos: " + str(self.positive) + "  " + str(self.url)

    def get_compound(self):
        return self.compound





