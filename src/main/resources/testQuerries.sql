
SELECT transaction_date FROM users INNER JOIN transactions ON(users.transaction_id = transactions.id) WHERE client = '3-95179';
SELECT transaction_date FROM users INNER JOIN transactions ON(users.transaction_id = transactions.id) WHERE transaction_date::text LIKE '2020-05-01 __:__:__' AND client = '3-95179';



--  SELECT users.client, transactions.id, transaction_date, oper_result, oper_type, amount FROM users INNER JOIN transactions ON(users.transaction_id = transactions.id) WHERE users.client = '3-51785';
