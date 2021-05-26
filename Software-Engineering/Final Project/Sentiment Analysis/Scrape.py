import requests
import io
import os
import threading
import time
import schedule



def trim(badformat):
    #badformat = badformat.replace(' ', '')
    badformat = badformat.replace('\t', '')
    badformat = badformat.replace('\r', '')
    badformat = badformat.replace('\n', '')
    return badformat
text = []
pages = []

def scrape(site, formatting):
    print(site)
    headers = {
        'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36'}
    page = requests.get(site, headers=headers)
    page = page.text
    page = page.replace(u"\u2019", "'")
    formatting.pop(0)
    # text will hold the content to be analyzed

    formats_to_scrape = []
    formatter = []
    for line in formatting:
        if line != '\n':
            formatter.append(line)
        else:
            formats_to_scrape.append(formatter.copy())
            formatter = []

    count = 0

    for this_format in formats_to_scrape:
        datapoint = ''
        index = 0
        index = page.find(this_format[0], index)

        index = 0
        if count == 0:
            substring = page[page.find(this_format[1], index): page.find(this_format[2], page.find(this_format[1]) +1)]
            # takes a portion of the page inat includes all of the content searched for in this iteration
            #substring = page[page.find(this_format[1], index): page.find(this_format[2], page.find(this_format[1]))]

            while substring.find(this_format[3], index) != -1:
                content_start = substring.find(this_format[3], index)
                content_end = substring.find(this_format[4], content_start + len(this_format[3]))
                #datapoint = substring[(content_start + len("content= ")): content_end]
                datapoint = substring[(content_start + len(this_format[3])): content_end]
                index = content_end
                # i had to do this because i got pissed leave it
                if datapoint.__contains__('p>'):
                     datapoint = datapoint[2: len(datapoint)]
                elif datapoint.__contains__('an>'):
                    datapoint = datapoint[3: len(datapoint)]
                elif datapoint.__contains__('\t'):
                    datapoint = datapoint.replace('\t', '')

                if datapoint.__contains__('\n'):
                    datapoint.replace('\n', ' ')
                if datapoint.__contains__('\r'):
                    datapoint.replace('\r', ' ')

                datapoint += '\n' + site
                text.append(datapoint + '\n')
        else: # used for the comments
            page = page.text

    for i in text:
        if i == '\n' or i == 'non\n':
            text.remove(i)

    for i in text:
        for j in i:
            if j.__contains__('\n'):
                datapoint.replace('\n', ' ')
            if j.__contains__('\r'):
                datapoint.replace('\n', ' ')

    dire = " "
    ct = site[site.find(".") + 1: site.find(".com")] + "/"
    #ory = str(datetime.datetime.now())
    #ory = ory[0:10] + "-" + ory[11:19] + "/"
    #ory = ory.replace(':', '.')
    #filename = dire + ct + ory + "output.txt"
    filename = '/Users/TheWalrus/Downloads/SentimentScraping/finalOutput.txt'

    os.makedirs(os.path.dirname(filename), exist_ok=True)
    with open(filename, "w") as f:
        for line in text:
            f.write(line)
    f.close()


def crawl(URL, formatting):
    headers = {
        'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36'
    }
    page = requests.get(URL, headers=headers)

    page = page.text

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
        if page_to_scrape not in pages:
            pages.append(page_to_scrape)

        for op in operations:
            if op == 'None':
                content.append(page_to_scrape)
            if op == '+':
                content.append(URL + page_to_scrape)
            if op == '+=':
                content.append(URL)
                content.append(URL + page_to_scrape)
    # content now holds complete web addresses for pages to be scraped. Dispatch threads to do so.
    crawled_threads = []

    for site in content:
        crawled_threads.append(threading.Thread(target=scrape, args=(site, formatting.copy())))
    for thread in crawled_threads:
        time.sleep(2.5)
        text = []
        thread.start()
    for thread in crawled_threads:
        thread.join()


# read in base addresses from file
URL = ''

def main():
    formatting = []
    threadsList = []
    with io.open('addresses.txt', 'r') as file:
        for line in file:
            if line.__contains__('://'):
                URL = trim(line)
                continue
            if line != '/////End of Formatting/////':
                if line == '\n':
                    formatting.append(line)
                else:
                    formatting.append(line.strip())
            if line.__contains__('/////End of Formatting/////'):
                isAddress = True
                if formatting[0] == 'crawl':
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
    import sentimentAnalysis
    sentimentAnalysis
main()
# schedule.every(86400).seconds.do(main)
# while 1:
#     schedule.run_pending()
#     time.sleep(60)