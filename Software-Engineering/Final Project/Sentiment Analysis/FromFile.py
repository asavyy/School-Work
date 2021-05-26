import requests
import io
import os
import threading
import datetime
import time
import re

def trim(badformat):
    badformat = badformat.replace(' ', '')
    badformat = badformat.replace('\t', '')
    badformat = badformat.replace('\r', '')
    badformat = badformat.replace('\n', '')
    return badformat


def scrape(page, site, formatting):
    time.sleep(2.5)
    print("got here3")
    formatting.pop(0)
    # text will hold the content to be analyzed
    text = []
    formats_to_scrape = []
    formatter = []
    for line in formatting:
        if line != '\n':
            formatter.append(line)
        else:
            formats_to_scrape.append(formatter.copy())
            formatter = []

    print(site)
    for this_format in formats_to_scrape:
        datapoint = ''
        index = 0
        index = page.find(this_format[0], index)
        # takes a portion of the page inat includes all of the content searched for in this iteration
        substring = page[page.find(this_format[1], index): page.find(this_format[2], page.find(this_format[1], index))]
        index = 0
        print(this_format)
        while substring.find(this_format[3], index) != -1:
            content_start = substring.find(this_format[3], index)
            content_end = substring.find(this_format[4], content_start)
            datapoint = datapoint + substring[(content_start + len(content_start)): content_end]
        text.append(datapoint)

    print("got here4")

    dire = "/WebOutput/"
    ct = site[site.find(".") + 1: site.find(".com")] + "/"
    ory = str(datetime.datetime.now())
    ory = ory[0:10] + "-" + ory[11:19] + "/"
    ory = ory.replace(':', '.')
    filename = dire + ct + ory + "output.txt"
    print(filename)
    os.makedirs(os.path.dirname(filename), exist_ok=True)
    with open(filename, "w") as f:
        for line in text:
            f.write(line)
        f.write('~!~')
    f.close()


def crawl(page, URL, formatting):
    # remove crawl operation from list
    formatting.pop(0)
    # get formatting for only the crawler
    crawl_formatting = []
    for specifier in formatting:
        if specifier != "\n":
            crawl_formatting.append(specifier)
        else:
            break
    for item in range(len(crawl_formatting) + 1):
        formatting.pop(0)
    # get any operations that need to be performed to get a complete web address
    operations = []
    for specifier in formatting:
        if specifier != "\n":
            operations.append(specifier)
        else:
            break
    for item in range(len(operations) +1):
        formatting.pop(0)

    # remaining info in formatting is used when scraping the pages crawled to

    content = []

    index = 0
    # use formatting to find all pages to be scraped
    print(URL)
    try_string = crawl_formatting[0]
    while page.find(try_string, index) != -1:
        index = page.find(crawl_formatting[0], index+1)
        content_start = page.find(crawl_formatting[1], index)
        content_end = page.find(crawl_formatting[2], (content_start + len(crawl_formatting[1])))
        index = content_end

        # ----------------------|position of content_start
        # ----------------------|-----|position of content_start + len(content_start)
        # ----------------------|-----|--------------------------------|position of content end
        # ----------------------|----|URL passed to this method
        # https://old.reddit.com/r/UPJ/comments/kb7958/thoughts_on_upj/
        page_to_scrape = page[(content_start + len(crawl_formatting[1])): content_end]

        for op in operations:
            if op == 'None':
                content.append(page_to_scrape)
            if op == '+':
                content.append(URL + page_to_scrape)
                # print(URL + page_to_scrape)

    # content now holds complete web addresses for pages to be scraped. Dispatch threads to do so.
    crawled_threads = []
    for site in content:
        print(site)
        # crawled_threads.append(threading.Thread(target=scrape, args=(site, formatting.copy())))
    for thread in crawled_threads:
        time.sleep(2.5)
        thread.start()
    for thread in crawled_threads:
        thread.join()


# local copy of reddit directory page
page = ''
with io.open('/Users/TheWalrus/PycharmProjects/SentimentScraping/'
             'FromFile/University of Pittsburgh at Johnstown.txt', 'r', encoding='utf-8') as file:
    page = file.read()
file.close()
URL = ''
formatting = []

isAddress = True
with io.open('addresses.txt', 'r') as file:
    for line in file:
        if isAddress:
            isAddress = False
            URL = trim(line)
            continue
        if line != '/////End of Formatting/////':
            if line == '\n':
                formatting.append(line)
            else:
                formatting.append(trim(line))
        if line == '/////End of Formatting/////':
            isAddress = True
            if formatting[0] == 'crawl':
                crawl(page, URL, formatting.copy())
                formatting = []
            else:
                scrape(page, URL, formatting.copy())
                formatting = []
file.close()


