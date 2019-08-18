package com.baseball;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.Bag;

import java.util.HashMap;

public class BaseballElimination {
    // create a baseball division from given filename in format specified below
    private int n;
    private HashMap<String, Integer> teamName2Id;
    private String[] teams;
    private int[] wins;
    private int[] loss;
    private int[] remain;
    private int[][] board;
    private int maxWin = -1;
    private FordFulkerson cacheFF;
    private String cacheTeam;
    private int teamVertexStartPos;

    public BaseballElimination(String filename) {
        In in = new In(filename);
        n = in.readInt();
        teamName2Id = new HashMap<>();
        teams = new String[n];
        wins = new int[n];
        loss = new int[n];
        remain = new int[n];
        board = new int[n][n];
        for (int i = 0; i < n; i++) {
            String teamName = in.readString();
            teamName2Id.put(teamName, i);
            teams[i] = teamName;
            wins[i] = in.readInt();
            loss[i] = in.readInt();
            remain[i] = in.readInt();
            if (wins[i] > maxWin) maxWin = wins[i];
            for (int j = 0; j < n; j++)
                board[i][j] = in.readInt();
        }

    }

    // number of teams
    public int numberOfTeams() {
        return n;
    }

    // all teams
    public Iterable<String> teams() {
        return teamName2Id.keySet();
    }

    // number of wins for given team
    public int wins(String team) {
        if (!teamName2Id.containsKey(team)) throw new IllegalArgumentException();
        int teamId = teamName2Id.get(team);
        return wins[teamId];
    }

    // number of losses for given team
    public int losses(String team) {
        if (!teamName2Id.containsKey(team)) throw new IllegalArgumentException();
        int teamId = teamName2Id.get(team);
        return loss[teamId];
    }

    // number of remaining games for given team
    public int remaining(String team) {
        if (!teamName2Id.containsKey(team)) throw new IllegalArgumentException();
        int teamId = teamName2Id.get(team);
        return remain[teamId];
    }

    // number of remaining games between team1 and team2
    public int against(String team1, String team2) {
        if (!teamName2Id.containsKey(team1) || !teamName2Id.containsKey(team2)) throw new IllegalArgumentException();
        int teamId1 = teamName2Id.get(team1);
        int teamId2 = teamName2Id.get(team2);
        return board[teamId1][teamId2];
    }

    private double cacheFF(String team) {
        int potential = wins(team) + remaining(team);
        int teamId = teamName2Id.get(team);
        int vertexNum = 2 + (n - 1) * n / 2;
        int sPos = vertexNum - 2;
        int tPos = vertexNum - 1;
        teamVertexStartPos = vertexNum - 1 - n;
        FlowNetwork fw = new FlowNetwork(vertexNum);

        int count = 0;
        FlowEdge edge;
        double fullFlow = 0;
        for (int i = 0; i < n - 1; i++) {
            int rowI = i;
            if (i >= teamId) rowI++;
            for (int j = i + 1; j < n - 1; j++) {
                int colJ = j;
                if (j >= teamId) colJ++;
                double oneFlow = (double) board[rowI][colJ];
                edge = new FlowEdge(sPos, count, oneFlow);
                fullFlow += oneFlow;
                fw.addEdge(edge);
                edge = new FlowEdge(count, i + teamVertexStartPos, Double.POSITIVE_INFINITY);
                fw.addEdge(edge);
                edge = new FlowEdge(count, j + teamVertexStartPos, Double.POSITIVE_INFINITY);
                fw.addEdge(edge);
                count++;
            }

        }
        for (int i = 0; i < n - 1; i++) {
            int tmpTeamId = i;
            if (i >= teamId) tmpTeamId++;
            edge = new FlowEdge(i + teamVertexStartPos, tPos, Math.max((double) (potential - wins[tmpTeamId]), 0));
            fw.addEdge(edge);
        }
        cacheFF = new FordFulkerson(fw, sPos, tPos);
        cacheTeam = team;
        return fullFlow;
    }

    // is given team eliminated?
    public boolean isEliminated(String team) {
        if (!teamName2Id.containsKey(team)) throw new IllegalArgumentException();
        int potential = wins(team) + remaining(team);
        if (potential < maxWin) return true;
        double fullFlow = cacheFF(team);
        double maximumFlow = cacheFF.value();
        return Math.abs(maximumFlow - fullFlow) >= 0.00001;
    }

    // subset R of teams that eliminates given team; null if not eliminated
    public Iterable<String> certificateOfElimination(String team) {
        if (!teamName2Id.containsKey(team)) throw new IllegalArgumentException();
        if (!isEliminated(team)) return null;
        int teamId = teamName2Id.get(team);

        Bag<String> bag = new Bag<>();
        int teamIdPos;
        if (cacheFF == null || !team.equals(cacheTeam))
            cacheFF(team);
        for (int i = 0; i < n - 1; i++) {
            teamIdPos = i;
            if (i >= teamId) teamIdPos++;
            if (cacheFF.inCut(i + teamVertexStartPos))
                bag.add(teams[teamIdPos]);
        }

        int potential = wins(team) + remaining(team);
        for (int i = 0; i < n; i++) {
            if (wins[i] > potential)
                bag.add(teams[i]);
        }
        return bag;
    }

    public static void main(String[] args) {
    }
}

