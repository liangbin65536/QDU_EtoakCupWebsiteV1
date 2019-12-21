# -*- coding: utf-8 -*-
import requests
from lxml import etree
import time
import re
import pymysql
import uuid

class newCoder:
    def __init__(self):
        #牛客网的账号密码
        self.email = "xxx@xx.com"
        self.pwd = "xxx"
        self.remember = "false"
        self.session = requests.Session()
        self.headers = {
            'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.109 Safari/537.36'
        }

    def login(self):
        global cookie
        loginurl = 'https://www.nowcoder.com/login/do?token='
        data = {
            "email": self.email,
            "pwd": self.pwd,
            "remember": self.remember
        }
        response = requests.post(loginurl, data)
        print(response.status_code)
        cookie = response.cookies.get_dict()
        print(cookie)
        self.getJavaLevel1Subject()

    # Java | 五星难度 | 不限来源 | 30题
    def getJavaLevel1Subject(self):
        global cookie
        db = pymysql.connect(host="localhost", user="xxx", passwd="xxx", db="etoakcup", charset="utf8")
        cursor = db.cursor()

        def get_info(subject):
            url = 'https://www.nowcoder.com' + subject
            html = requests.get(url, cookies=cookie).content
            # 获取问题
            question = etree.HTML(html.decode('UTF-8', 'ignore')).xpath("//div[@class='question-main']")[0].xpath('string(.)').strip().replace("\n", "").replace("\t", " ")
            print(question)
            # 获取正确答案
            answer = etree.HTML(html).xpath("/html/body/div[1]/div[2]/div[2]/div[3]/h1/text()")[0]
            right_ans = ''.join(re.split(r'[^A-Za-z]', answer))
            print(right_ans)
            # 获取选项
            options = []
            for i in etree.HTML(html).xpath(
                    "//div[@class='result-answer-item']|//div[@class='result-answer-item green-answer-item']"):
                options.append(i.xpath('string(.)').strip())
            option_string = '#'.join(options)
            print(option_string)

            subject_id = str(uuid.uuid1()).replace("-", "")

            sql = "insert into subject (`id`, `language`, `question`, `options`, `answer`, `level`) values ('%s', '%s', '%s', '%s', '%s', '%d')" % (
                subject_id, 'Java', question, option_string, right_ans, 5)
            try:
                cursor.execute(sql)
                db.commit()
            except Exception as err:
                print(err)
            finally:
                db.rollback()



        # 模拟生成题目操作
        url1 = "https://www.nowcoder.com/makePaper?source=3&tagIds=570&difficulty=5&questionCount=30"
        re_html1 = requests.post(url1, cookies=cookie).content
        js_info = etree.HTML(re_html1.decode('UTF-8', 'ignore')).xpath('/html/body/div[1]/script/text()')[0]
        subject_info = str(js_info.encode(), encoding="utf8")
        start_tag1 = subject_info.find("pid")
        start_tag2 = subject_info.find("tid")
        start_tag3 = subject_info.find("qid")
        pid = subject_info[start_tag1+6:start_tag1+14]
        tid = subject_info[start_tag2+6:start_tag2+14]
        qid = subject_info[start_tag3+6:start_tag3+11]


        url2 = "https://www.nowcoder.com/question/next?pid=" + pid + "&qid=" + qid + "&tid=" + tid
        data = {
            "finish": "1"
        }
        requests.post(url2, data, cookies=cookie)
        url_main = "https://www.nowcoder.com/test/question/done?tid=" + tid + "&qid=" + qid
        html_main = requests.get(url_main, cookies=cookie).content
        all_subjects = []
        for t in etree.HTML(html_main.decode('UTF-8', 'ignore')).xpath("//li[@class='error-order']/a/@href"):
            all_subjects.append(t)
        for s in all_subjects:
            get_info(s)


newcoder = newCoder()
while 1:
    newcoder.login()
    time.sleep(20)
