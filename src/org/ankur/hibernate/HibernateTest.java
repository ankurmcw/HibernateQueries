package org.ankur.hibernate;

import java.util.List;

import org.ankur.hibernate.dto.UserDetail;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class HibernateTest {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		SessionFactory factory = null;
		Session session = null;
		try {
			factory = new Configuration().configure().buildSessionFactory();
			session = factory.openSession();
			session.beginTransaction();
			/*for(int i=1; i<=10; i++) {
				UserDetail entity = new UserDetail();
				entity.setUserName("User" + i);
				session.save(entity);
			}*/
			
			System.out.println("*****************************************");
			Query query = session.createQuery("from UserDetail");
			List<UserDetail> list = query.list();			
			for(UserDetail entity : list) {
				System.out.println(entity);
			}
			
			System.out.println("*****************************************");
			query.setFirstResult(5);
			query.setMaxResults(4);
			list = query.list();
			for(UserDetail entity : list) {
				System.out.println(entity);
			}
			
			System.out.println("*****************************************");
			query = session.createQuery("from UserDetail where id > ?");
			query.setParameter(0, 4);
			list = query.list();
			for(UserDetail entity : list) {
				System.out.println(entity);
			}
			
			System.out.println("*****************************************");
			query = session.createQuery("from UserDetail where id > :userId");
			query.setParameter("userId", 4);
			list = query.list();
			for(UserDetail entity : list) {
				System.out.println(entity);
			}
			
			System.out.println("*****************************************");
			query = session.getNamedQuery("UserDetail.byId");
			query.setParameter(0, 3);		
			UserDetail entity = (UserDetail) query.uniqueResult();
			System.out.println(entity);
			
			System.out.println("*****************************************");
			query = session.getNamedQuery("UserDetail.byNamedId");
			query.setParameter("userId", 5);	
			entity = (UserDetail) query.uniqueResult();
			System.out.println(entity);
			
			System.out.println("*****************************************");
			Criteria criteria = session.createCriteria(UserDetail.class)
					.add(Restrictions.eq("userName", "User9"));
			list = criteria.list();
			for(UserDetail entity1 : list) {
				System.out.println(entity1);
			}
			
			System.out.println("*****************************************");
			criteria = session.createCriteria(UserDetail.class)
					.add(Restrictions.between("id", 2, 6));
			list = criteria.list();
			for(UserDetail entity1 : list) {
				System.out.println(entity1);
			}
			
			System.out.println("*****************************************");
			criteria = session.createCriteria(UserDetail.class)
							  .add(Restrictions.or(Restrictions.between("id", 1, 3), Restrictions.between("id", 7, 9)));
			list = criteria.list();
			for(UserDetail entity1 : list) {
				System.out.println(entity1);
			}
			
			System.out.println("*****************************************");
			criteria = session.createCriteria(UserDetail.class)
							.setProjection(Projections.property("userName"));
			List<String> nameList = criteria.list();
			for(String userName: nameList) {
				System.out.println(userName);
			}
			
			System.out.println("*****************************************");
			criteria = session.createCriteria(UserDetail.class)
							.setProjection(Projections.property("id"))
							.addOrder(Order.desc("id"));
			List<Integer> idList = criteria.list();
			for(int userId: idList) {
				System.out.println(userId);
			}
			
			System.out.println("*****************************************");
			criteria = session.createCriteria(UserDetail.class)
					.setProjection(Projections.sum("id"));			
			Long sum = (Long) criteria.uniqueResult();
			System.out.println("Sum of id's: " + sum);
			
			// Query by example
			// Example query does not consider primary keys
			// Example query does not consider null properties
			System.out.println("*****************************************");			
			UserDetail exampleEntity = new UserDetail();
			exampleEntity.setUserName("User5");
			
			Example example = Example.create(exampleEntity);
			criteria = session.createCriteria(UserDetail.class).add(example);
			list = criteria.list();
			for(UserDetail entity1 : list) {
				System.out.println(entity1);
			}
			
			System.out.println("*****************************************");						
			exampleEntity.setUserName("User%");
			
			example = Example.create(exampleEntity).enableLike().excludeProperty("id");
			
			criteria = session.createCriteria(UserDetail.class).add(example);
			list = criteria.list();
			for(UserDetail entity1 : list) {
				System.out.println(entity1);
			}
			
			/*query = session.createQuery("select new map(id,name) from FourWheeler");
			List<Map<Integer,String>> list2 = (List<Map<Integer,String>>)query.list();
			for(Map<Integer,String> x: list2){
				for(Map.Entry<Integer, String> m: x.entrySet())
					System.out.println(m.getKey() + " : "+m.getValue());
			}
			System.out.println("*********************************");*/
			
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(session != null) {
				session.close();
			}
			if (factory != null) {
				factory.close();
			}
		}

	}

}
