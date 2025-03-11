--Получить всех пользователей, у которых баланс на всех счетах больше 10 000.
select u.* from user u
join account a on a.user_id = u.id and u.balance > 10000;

--Найти пользователя по email.
select u.* from user u where u.email = 'someEmail@mail.kg';
-- или
select u.* from user u where u.email like '%someEmail%'; -- если нужен примерный поиск

--Получить сумму всех средств в системе.
select sum(a.balance) from account;