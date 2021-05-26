import requests
import io
import os
import threading
import datetime
import time


def scrape(site, formatting):
    time.sleep(2.5)
    print("got here3")
    formatting.pop(0)
    # text will hold the content to be analyzed
    text = []
    formats_to_scrape = []
    formatter = []
    for line in formatting:
        if line != '':
            formatter.append(line)
        else:
            formats_to_scrape.append(formatter.copy())
            formatter = []

    page = requests.get(site)
    page = page.text
    print(site)
    for this_format in formats_to_scrape:
        datapoint = ''
        index = 0
        index = page.find(this_format[0], index)
        # takes a portion of the page inat includes all of the content searched for in this iteration
        print(page.find(this_format[1], index))
        print(page.find(this_format[2], page.find(this_format[1], index)))
        substring = page[page.find(this_format[1], index): page.find(this_format[2], page.find(this_format[1], index))]
        print(substring)
        index = 0
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


def crawl(URL, formatting):
    # remove crawl operation from list
    formatting.pop(0)
    # get formatting for only the crawler
    crawl_formatting = []
    for specifier in formatting:
        formatting.remove(specifier)
        if specifier != "":
            crawl_formatting.append(specifier)
        else:
            break

    # get any operations that need to be performed to get a complete web address
    operations = []
    for specifier in formatting:
        formatting.remove(specifier)
        if specifier != "":
            operations.append(specifier)
        else:
            break

    # remaining info in formatting is used when scraping the pages crawled to

    content = []

    page = requests.get(URL)
    page = page.text

    index = 0
    # use formatting to find all pages to be scraped
    print(URL)
    print(page)
    print(crawl_formatting[0])
    print(page.find(crawl_formatting[0], index))
    while page.find(crawl_formatting[0], index) != -1:
        index = page.find(crawl_formatting[0], index+1)
        content_start = page.find(crawl_formatting[1], index)
        content_end = page.find(crawl_formatting[2], index)
        index = content_end

        # ----------------------|position of content_start
        # ----------------------|-----|position of content_start + len(content_start)
        # ----------------------|-----|--------------------------------|position of content end
        # ----------------------|----|URL passed to this method
        # https://old.reddit.com/r/UPJ/comments/kb7958/thoughts_on_upj/
        page_to_scrape = page[(content_start + len(crawl_formatting[1])): content_end]
        print(page_to_scrape)
        print("hi")

        for op in operations:
            if op == 'None':
                content.append(page_to_scrape)
            if op == '+':
                content.append(URL + page_to_scrape)
                print(URL + page_to_scrape)

    # content now holds complete web addresses for pages to be scraped. Dispatch threads to do so.
    crawled_threads = []
    for site in content:
        crawled_threads.append(threading.Thread(target=scrape, args=(site, formatting.copy())))
    for thread in crawled_threads:
        time.sleep(2.5)
        thread.start()
    for thread in crawled_threads:
        thread.join()



# read in base addresses from file

URL = ''
formatting = []
threadsList = []

isAddress = True
with io.open('addresses.txt', 'r') as file:
    for line in file:
        if isAddress:
            isAddress = False
            URL = line
            continue
        if line != '/////End of Formatting/////':
            formatting.append(line)
        if line == '/////End of Formatting/////':
            isAddress = True
            if formatting[0] == 'crawl\n':
                threadsList.append(threading.Thread(target=crawl, args=(URL, formatting.copy())))
                formatting = []
            else:
                threadsList.append(threading.Thread(target=scrape, args=(URL, formatting.copy())))
                formatting = []
file.close()

for thread in threadsList:
    thread.start()
    time.sleep(2.5)

for thread in threadsList:
    thread.join()

