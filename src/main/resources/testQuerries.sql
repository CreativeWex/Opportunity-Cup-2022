SELECT oper_type, transaction_date, transaction_id FROM users INNER JOIN transactions ON(users.transaction_id = transactions.id) WHERE client = '3-95179' AND oper_type IN('Пополнение', 'Снятие') ORDER BY transaction_date;


SELECT transactions.id FROM users INNER JOIN
    transactions ON(users.transaction_id = transactions.id) WHERE transaction_date::text LIKE '2020-05-02 __:__:__' AND client = '1-98207';