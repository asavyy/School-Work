import io

page = ''
with io.open('University_of_Pittsburgh_at_Johnstown.txt', 'r', encoding='utf-8') as file:
    for line in file:
        page += line
file.close()
search_term_1 = 'thing_t3_'             # what I am actually searching for
search_term_2 = '<!DOCTYPE html'        # example
search_term_3 = '<!DOCTYPE html>'       # example
print(page)
print(page.find(search_term_1))                # this returns -1
print(page.find(search_term_2))                # also returns -1
print(page.find(search_term_3))                # returns 0, as it is the first thing in the document

print(type(page))                       # prints <class 'str'>
print(type(search_term_1))              # prints <class 'str'>