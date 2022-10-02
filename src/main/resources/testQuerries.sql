SELECT transactions.id, amount, oper_type, oper_result, account, account_valid_to, terminal, city FROM users INNER JOIN
    transactions ON(users.transaction_id = transactions.id) WHERE transaction_date::text LIKE '2020-05-01 __:__:__' AND client = '6-13938';


SELECT transactions.id FROM users INNER JOIN
    transactions ON(users.transaction_id = transactions.id) WHERE transaction_date::text LIKE '2020-05-02 __:__:__' AND client = '1-98207';