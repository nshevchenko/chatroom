/*
 * JBoss, Home of Professional Open Source
 * Copyright 2015, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.as.quickstarts.model;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

public class ManagedBeanUserDao implements UserDao {

    @Inject
    private EntityManager entityManager;

    @Inject
    private UserTransaction utx;

    public void createUser(User user) {
        try {
            try {
                utx.begin();
                entityManager.persist(user);
            } finally {
                utx.commit();
            }
        } catch (Exception e) {
            try {
                utx.rollback();
            } catch (SystemException se) {
                throw new RuntimeException(se);
            }
            throw new RuntimeException(e);
        }
    }

    public void loggedInTrue(User user) {
        try {
            try {
                utx.begin();
                String query_string = "UPDATE User u SET loggedIn=true WHERE u.username = :username";
                Query query = entityManager.createQuery(query_string);
                query.setParameter("username", user.getUsername());
                query.executeUpdate();
            } finally {
                utx.commit();
            }
        } catch (Exception e) {
            try {
                utx.rollback();
            } catch (SystemException se) {
                throw new RuntimeException(se);
            }
            throw new RuntimeException(e);
        }
    }

    public void loggedInFalse(User user) {
        try {
            try {
                utx.begin();
                String query_string = "UPDATE User u SET loggedIn=false WHERE u.username = :username";
                Query query = entityManager.createQuery(query_string);
                query.setParameter("username", user.getUsername());
                query.executeUpdate();
                //entityManager.refresh(user);
            } finally {
                utx.commit();
            }
        } catch (Exception e) {
            try {
                utx.rollback();
            } catch (SystemException se) {
                throw new RuntimeException(se);
            }
            throw new RuntimeException(e);
        }
    }



    public void privacy(User user) {
        try {
            try {
                utx.begin();
                String query_string = "UPDATE User u SET privacy=:privacy WHERE u.username = :username";
                Query query = entityManager.createQuery(query_string);
                query.setParameter("privacy", user.getPrivacy());
                query.setParameter("username", user.getUsername());
                query.executeUpdate();
                //entityManager.refresh(user);
            } finally {
                utx.commit();
            }
        } catch (Exception e) {
            try {
                utx.rollback();
            } catch (SystemException se) {
                throw new RuntimeException(se);
            }
            throw new RuntimeException(e);
        }
    }

    public void addFriend(User user) {
        try {
            try {
                utx.begin();
                String query_string = "UPDATE User u SET friends=:friends WHERE u.username = :username";
                Query query = entityManager.createQuery(query_string);
                System.out.println("user.getFriendsStr() " + user.getFriendsStr());
                query.setParameter("friends", user.getFriendsStr());
                query.setParameter("username", user.getUsername());
                query.executeUpdate();
                //entityManager.refresh(user);
            } finally {
                utx.commit();
            }
        } catch (Exception e) {
            try {
                System.out.println("exceptin " + e);
                utx.rollback();
            } catch (SystemException se) {
                System.out.println("exceptin " + se);
                throw new RuntimeException(se);
            }
            throw new RuntimeException(e);
        }
    }

    public void removeFriend(User user) {
        try {
            try {
                utx.begin();
                String query_string = "UPDATE User u SET friends=:friends WHERE u.username = :username";
                Query query = entityManager.createQuery(query_string);
                query.setParameter("friends", user.getFriendsStr());
                query.setParameter("username", user.getUsername());
                query.executeUpdate();
                //entityManager.refresh(user);
            } finally {
                utx.commit();
            }
        } catch (Exception e) {
            try {
                utx.rollback();
            } catch (SystemException se) {
                throw new RuntimeException(se);
            }
            throw new RuntimeException(e);
        }
    }


}
