import numpy as np

from Values import *
from nltk.sentiment.vader import SentimentIntensityAnalyzer
from fpdf import FPDF
import matplotlib.pyplot as plt
from datetime import date



def graphStuff():
    height = [pos, neg]
    bars = ('Positive', 'Negative')
    y_pos = np.arange(len(bars))
    plt.figure(figsize=(7, 6), dpi=80)
    # Create bars
    plt.bar(y_pos, height)

    # Create names on the x-axis
    plt.xticks(y_pos, bars)

    # save graph as a pdf then add it to the main pdf file
    plt.savefig("/Users/TheWalrus/Downloads/SentimentScraping/barchart.png")
    pdf.image('/Users/TheWalrus/Downloads/SentimentScraping/barchart.png')


def checkInput(message):
    message = message.upper()
    message = message.replace("PARTY", "DRINK")
    message = message.replace("PARTYING", "DRINKING")


def sentiment(message_text, website_text):
    # next, we initialize VADER so we can use it within our Python script
    sid = SentimentIntensityAnalyzer()
    # the variable 'message_text' now contains the text we will analyze.
    # Calling the polarity_scores method on sid and passing in the message_text outputs a dictionary with negative,
    # neutral, positive, and compound scores for the input text
    scores = sid.polarity_scores(message_text)
    list_scores = list(scores.values())
    print(list_scores)
    # add values to a an object instead of an array
    # i dont know why it is this order but it works
    values.append(Values(list_scores[3], list_scores[0], list_scores[1], list_scores[2], website_text))


# VADER also sums all weighted scores to calculate a “compound” value normalized between -1 and 1;
# this value attempts to describe the overall affect of the entire text from strongly negative (-1) to
# strongly positive (1).
reviews_list = []
website_list = []
values = []  # holds the segimented values

# pdf stuff
pdf = FPDF()
pdf.add_page()
pdf.set_font("Arial", size=16)

# read in data from scraping
file_reviews = open("/Users/TheWalrus/Downloads/SentimentScraping/finalOutput.txt", "r")
review = file_reviews.readline()
website = file_reviews.readline()

while review != "" or review == "\n":
    reviews_list.append(review)
    review = file_reviews.readline()
    website_list.append(website)
    website = file_reviews.readline()


# header
pdf.cell(0, 10, "University of Pittsburgh Daily Reviews " + str(date.today()), 0, 1, "C")

# counts how many pos, neg, and neutral reviews
pos = 0
neg = 0
for i in range(0, len(reviews_list)):
    sentiment(reviews_list[i], website_list[i])
    # prints the output of the segmentation analysis
    # determine if the review is pos, neg, or neu ands 1 for the graph
    if (getattr(values[i], 'compound')) != 0.0:
        pdf.multi_cell(0, 5, reviews_list[i])
        pdf.multi_cell(0, 8, str(values[i]), 0, 3)
        pdf.cell(0, 8, "", 0, 2) # adds a space between reviews

        if (getattr(values[i], 'compound')) > .01:
            pos += 1
        elif (getattr(values[i], 'compound')) < -.01:
            neg += 1

graphStuff()

pdf.output("/Users/TheWalrus/Downloads/SentimentScraping/output.pdf")
