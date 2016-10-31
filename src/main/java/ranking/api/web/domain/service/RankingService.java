package ranking.api.web.domain.service;

import org.springframework.stereotype.Service;
import ranking.api.web.domain.model.RankingData;

import java.util.*;
import java.util.concurrent.PriorityBlockingQueue;

@Service
public class RankingService {
    final static int PRIORITY_QUEUE_BIG_SIZE = 1000000;
    final static int PRIORITY_QUEUE_SMALL_SIZE = 10;
    final static PriorityBlockingQueue<RankingData> rankingQueue = new PriorityBlockingQueue<RankingData>(PRIORITY_QUEUE_BIG_SIZE, new DataComparator());
    final static Map<String, Integer> rankingData = new HashMap<String, Integer>();
    final static Map<String, LinkedList<String>> friend = new HashMap<String, LinkedList<String>>();

    public void insert(String id, int score) {
        rankingData.put(id, score);
        if(!friend.containsKey(id)) {
            friend.put(id, new LinkedList<String>());
            rankingQueue.add(new RankingData(id, score));
        }
        else {
            final Iterator<RankingData> rankingQueueIterator = rankingQueue.iterator();
            while(rankingQueueIterator.hasNext()) {
                if(rankingQueueIterator.next().getId().equals(id)) {
                    rankingQueueIterator.remove();
                    rankingQueue.add(new RankingData(id, score));
                    break;
                }
            }
        }
    }

    public void register(String myId, String friendId) {
        final LinkedList<String> friendList = friend.get(myId);
        if (!friendList.contains(friendId))
            friendList.add(friendId);
        friend.put(myId, friendList);
    }

    public void delete(String myId, String friendId) {
        final LinkedList<String> friendList = friend.get(myId);
        friendList.remove(friendId);
    }

    public List<String> getAllRankingList() {
        final List<String> allRankingList = priorityQueueToList(rankingQueue);

        return allRankingList;
    }

    public List<String> getMyRanking(String myId) {
        final PriorityBlockingQueue<RankingData> myRankingQueue = new PriorityBlockingQueue<RankingData>(PRIORITY_QUEUE_SMALL_SIZE, new DataComparator());
        final LinkedList<String> myRankingList = new LinkedList<String>();
        int rank = 0;
        myRankingQueue.addAll(rankingQueue);

        while(!myRankingQueue.isEmpty()) {
            final RankingData rankingData = myRankingQueue.poll();
            final String id = rankingData.getId();
            int score = rankingData.getScore();
            ++rank;

            if(id.equals(myId)) {
                final String myRanking = rank + ":" + id + ":" + score;
                myRankingList.add(myRanking);
                break;
            }
        }
        return myRankingList;
    }

    public List<String> getFriendRankingList(String myId) {
        final LinkedList<String> myFriendList = friend.get(myId);
        final PriorityBlockingQueue<RankingData> myFriendRankingQueue = new PriorityBlockingQueue<RankingData>(PRIORITY_QUEUE_SMALL_SIZE, new DataComparator());
        final Iterator<String> myFriendListIterator = myFriendList.iterator();

        while (myFriendListIterator.hasNext()) {
            final String friendId = myFriendListIterator.next();
            myFriendRankingQueue.add(new RankingData(friendId, rankingData.get(friendId)));
        }
        myFriendRankingQueue.add(new RankingData(myId, rankingData.get(myId)));

        final List<String> friendRankingList = priorityQueueToList(myFriendRankingQueue);

        return friendRankingList;
    }

    public List<String> getFriendList(String myId) {
        final LinkedList<String> myFriendList = friend.get(myId);

        return myFriendList;
    }

    private  List<String> priorityQueueToList(PriorityBlockingQueue myPriorityBlockingQueue) {
        final PriorityBlockingQueue<RankingData> priorityBlockingQueue = new PriorityBlockingQueue<RankingData>(PRIORITY_QUEUE_BIG_SIZE, new DataComparator());
        final List<String> list = new LinkedList<String>();
        int rank = 0;
        priorityBlockingQueue.addAll(myPriorityBlockingQueue);

        while(!priorityBlockingQueue.isEmpty()) {
            final RankingData rankData = priorityBlockingQueue.poll();
            final String id = rankData.getId();
            final int score = rankData.getScore();
            final String result = ++rank + ":" + id + ":" + score;
            list.add(result);
        }
        return list;
    }

    private static class DataComparator implements Comparator<RankingData> {
        public int compare(RankingData x, RankingData y) {
            if(x.getScore() > y.getScore())
                return -1;
            else if(x.getScore() < y.getScore())
                return 1;
            else
                return 0;
        }
    }
}