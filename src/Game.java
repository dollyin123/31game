public class Game {
    int[] learnProbability, resultProbability, com1Pattern, com2Pattern;
    int round, pass;
    Thread com1Thread, com2Thread;

    public Game() {
        startSetting();
        Com1 com1 = new Com1();
        com1Thread = new Thread(com1);
        com1Thread.start();
        while(true) {
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
            }
        }
    }

    public static void main(String... args) {
        new Game();
    }

    class Com1 implements Runnable {
        @Override
        public void run() {
            try {
                pass = 2;
                while(pass != 0) {
                    if(passOrGet(round)) {
                        com1Pattern[round] += 1;
                        round += 1;
                        pass = 0;
                    } else {
                        com1Pattern[round] -= 1;
                        round += 1;
                        pass -= 1;
                        if(pass == 0) {
                            com1Pattern[round] += 1;
                            round += 1;
                        }
                    }
                    if(round == 30) {
                        newSetting(0);
                        pass = 2;
                    }
                }
                Com2 com2 = new Com2();
                com2Thread = new Thread(com2);
                com2Thread.start();
            } catch (Exception e) {
            } finally {}
        }
    }

    class Com2 implements Runnable {
        @Override
        public void run() {
            try {
                pass = 2;
                while(pass != 0) {
                    if(passOrGet(round)) {
                        com2Pattern[round] += 1;
                        round += 1;
                        pass = 0;
                    } else {
                        com2Pattern[round] -= 1;
                        round += 1;
                        pass -= 1;
                        if(pass == 0) {
                            com2Pattern[round] += 1;
                            round += 1;
                        }
                    }
                    if(round == 30) {
                        newSetting(1);
                    }
                }
                Com1 com1 = new Com1();
                com1Thread = new Thread(com1);
                com1Thread.start();
            } catch (Exception e) {
            } finally {}
        }
    }

    void startSetting() {
        learnProbability = new int[30];
        for(int i=0; i<learnProbability.length; i++) {
            learnProbability[i] = 50;
        }
        learnProbability[29] = 100;


        com1Pattern = new int[30];
        for(int i=0; i<com1Pattern.length; i++) {
            com1Pattern[i] = 0;
        }

        com2Pattern = new int[30];
        for(int i=0; i<com2Pattern.length; i++) {
            com2Pattern[i] = 0;
        }

        round = 0;
    }

    void newSetting(int winner) {
//		resultProbability = new int[30];
//		for(int i=0; i<learnProbability.length; i++) {
//			resultProbability[i] = 0;
//			if(learnProbability[i]>95) {
//				resultProbability[i] = 100;
//			}
//			System.out.print(" " + resultProbability[i]);
//		}
//		System.out.println();

        for(int i=0; i<learnProbability.length; i++) {
            System.out.print(" " + learnProbability[i]);
        }
        System.out.println();

        if(winner == 0) {
            for(int i=0; i<com1Pattern.length; i++) {
                if(com1Pattern[i] == 1) {
                    learnProbability[i] += 1;
                } else if(com1Pattern[i] == -1) {
                    learnProbability[i] -= 1;
                }
                if(com2Pattern[i] == 1) {
                    learnProbability[i] -= 1;
                } else if(com2Pattern[i] == -1) {
                    learnProbability[i] += 1;
                }
            }
        } else {
            for(int i=0; i<com2Pattern.length; i++) {
                if(com1Pattern[i] == 1) {
                    learnProbability[i] -= 1;
                } else if(com1Pattern[i] == -1) {
                    learnProbability[i] += 1;
                }
                if(com2Pattern[i] == 1) {
                    learnProbability[i] += 1;
                } else if(com2Pattern[i] == -1) {
                    learnProbability[i] -= 1;
                }
            }
        }

        for(int i=0; i<learnProbability.length; i++) {
            if(learnProbability[i] > 100) {
                learnProbability[i] = 100;
            }
            if(learnProbability[i] < 0) {
                learnProbability[i] = 0;
            }
        }

        com1Pattern = new int[30];
        for(int i=0; i<com1Pattern.length; i++) {
            com1Pattern[i] = 0;
        }

        com2Pattern = new int[30];
        for(int i=0; i<com2Pattern.length; i++) {
            com2Pattern[i] = 0;
        }

        round = 0;
    }

    Boolean passOrGet(int round) {
        int random = (int) ((Math.random()*100)+1);
        if(random <= learnProbability[round]) {
            return true;
        } else {
            return false;
        }
    }

}
