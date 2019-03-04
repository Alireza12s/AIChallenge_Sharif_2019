package client;

import client.model.*;

import java.util.Random;

import static client.model.AbilityName.*;

public class AI {

    Cell oz[];
    Cell rz[];
    Cell z1, z2, z3, z4;

    private Random random = new Random();

    public void preProcess(World world) {
        System.out.println("pre process started");
        Map map = world.getMap();
        oz = map.getObjectiveZone();
        rz = map.getMyRespawnZone();
    }

    public void pickTurn(World world) {
        if (world.getCurrentTurn() == 0) {
            world.pickHero(HeroName.BLASTER);
        } else if (world.getCurrentTurn() == 1) {
            world.pickHero(HeroName.BLASTER);
        } else if (world.getCurrentTurn() == 2) {
            world.pickHero(HeroName.BLASTER);
        } else if (world.getCurrentTurn() == 3) {
            world.pickHero(HeroName.BLASTER);
        }
    }

    public void moveTurn(World world) {
        if (world.getCurrentTurn() >= 4) {
            System.out.println("move started");
            Hero[] heroes = world.getMyHeroes();
            int l = oz.length;
            Cell b[] = new Cell[4];


            for (int i = 0; i < 4; i++) {
                b[i] = heroes[i].getCurrentCell();
            }

            Map map = world.getMap();
            z1 = oz[0];
            z3 = oz[oz.length - 1];

            int column1 = z1.getColumn();
            int row1 = z1.getRow();
            int row0 = row1;
            int column3 = z3.getColumn();
            int row3 = z3.getRow();
            int row5 = row3;
            int column2 = column1;
            int row2 = row1;
            int column4 = column3;
            int row4 = row3;

            //z1
            while (map.getCell(row1, column1).isInObjectiveZone()) {
                row1++;
            }
            row1--;
            z1 = map.getCell(row0 + (row1 - row0) / 2, column1);

            //z2
            while (map.getCell(row2, column2).isInObjectiveZone()) {
                column2++;
            }
            column2--;
            z2 = map.getCell(row2, column1 + (column2 - column1) / 2);

            //z3
            while (map.getCell(row3, column3).isInObjectiveZone()) {
                row3--;
            }
            row3++;
            z3 = map.getCell(row5 - (row5 - row3) / 2, column3);

            //z4
            while (map.getCell(row4, column4).isInObjectiveZone()) {
                column4--;
            }
            column4++;
            z4 = map.getCell(row4, column3 - (column3 - column4) / 2);


//            Direction point1[] = world.getPathMoveDirections(heroes[0].getCurrentCell(), z1, b);
//            Direction point2[] = world.getPathMoveDirections(heroes[1].getCurrentCell(), z2, b);
//            Direction point3[] = world.getPathMoveDirections(heroes[2].getCurrentCell(), z3, b);
//            Direction point4[] = world.getPathMoveDirections(heroes[3].getCurrentCell(), z4, b);


            //1
            if (heroes[0].getCurrentHP() > 0) {
                if (world.getCurrentTurn() <= 6) {
                    Direction point1[] = world.getPathMoveDirections(heroes[0].getCurrentCell(), z1);
                    if (point1.length != 0) {
                        world.moveHero(heroes[0], point1[0]);
                    }
                } else {
                    Direction point1[] = world.getPathMoveDirections(heroes[0].getCurrentCell(), z1, b);
                    if (point1.length != 0) {
                        world.moveHero(heroes[0], point1[0]);
                    }
                }
            }


            //2
            if (heroes[1].getCurrentHP() > 0) {
                if (world.getCurrentTurn() <= 6) {
                    Direction point2[] = world.getPathMoveDirections(heroes[1].getCurrentCell(), z2);
                    if (point2.length != 0) {
                        world.moveHero(heroes[1], point2[0]);
                    }
                } else {
                    Direction point2[] = world.getPathMoveDirections(heroes[1].getCurrentCell(), z2, b);
                    if (point2.length != 0) {
                        world.moveHero(heroes[1], point2[0]);
                    }
                }
            }


            //3
            if (heroes[2].getCurrentHP() > 0) {
                if (world.getCurrentTurn() <= 6) {
                    Direction point3[] = world.getPathMoveDirections(heroes[2].getCurrentCell(), z3);
                    if (point3.length != 0) {
                        world.moveHero(heroes[2], point3[0]);
                    }

                } else {
                    Direction point3[] = world.getPathMoveDirections(heroes[2].getCurrentCell(), z3, b);
                    if (point3.length != 0) {
                        world.moveHero(heroes[2], point3[0]);
                    }
                }
            }

            //4
            if (heroes[3].getCurrentHP() > 0) {
                if (world.getCurrentTurn() <= 6) {
                    Direction point4[] = world.getPathMoveDirections(heroes[3].getCurrentCell(), z4);
                    if (point4.length != 0) {
                        world.moveHero(heroes[3], point4[0]);
                    }
                } else {
                    Direction point4[] = world.getPathMoveDirections(heroes[3].getCurrentCell(), z4, b);
                    if (point4.length != 0) {
                        world.moveHero(heroes[3], point4[0]);
                    }
                }

            }
        }
    }

    public void actionTurn(World world) {
        System.out.println("action started");
        Hero[] heroes = world.getMyHeroes();
        Hero[] doshman = world.getOppHeroes();
        Hero[] enemy = new Hero[4];
        for (int i = 0; i < 4; i++) {
            enemy[i] = null;
        }
        Map map = world.getMap();

        int z = -1;
        for (int i = 0; i < 4; i++) {
            if (doshman[i].getCurrentHP() != -1) {
                z++;
                enemy[z] = doshman[i];
            }
        }



        for (Hero hero : heroes) {
            if (z != -1) {
                int target = 0;
                int min = world.manhattanDistance(hero.getCurrentCell(), enemy[0].getCurrentCell());
                for (int i = 0; i < 4; i++) {
                    int temp = world.manhattanDistance(hero.getCurrentCell(), enemy[i].getCurrentCell());
                    if (min > temp) {
                        min = temp;
                        target = i;
                    }
                }

                int herocolumn = hero.getCurrentCell().getColumn();
                int herorow = hero.getCurrentCell().getRow();
                int enemycolumn = hero.getCurrentCell().getColumn();
                int enemyrow = hero.getCurrentCell().getRow();


                if (hero.getCurrentHP() <= 60 && hero.getAbility(BLASTER_DODGE).isReady()) {
                    if (hero.getCurrentCell().getColumn() == z1.getColumn()) {
                        world.castAbility(hero, hero.getAbility(BLASTER_DODGE), herorow - 2, herocolumn + 2);
                    } else if (hero.getCurrentCell().getRow() == z2.getRow()) {
                        world.castAbility(hero, hero.getAbility(BLASTER_DODGE), herorow + 2, herocolumn + 2);
                    } else if (hero.getCurrentCell().getColumn() == z3.getColumn()) {
                        world.castAbility(hero, hero.getAbility(BLASTER_DODGE), herorow + 2, herocolumn - 2);
                    } else if (hero.getCurrentCell().getRow() == z4.getRow()) {
                        world.castAbility(hero, hero.getAbility(BLASTER_DODGE), herorow - 2, herocolumn - 2);
                    }

                } else {
                    if (hero.getAbility(BLASTER_BOMB).isReady() && world.getAP() >= 25) {
                        if (min <= 5) {
                            world.castAbility(hero, hero.getAbility(BLASTER_BOMB), enemy[target].getCurrentCell());
                        } else if (min <= 7) {
                            if (Math.abs(herocolumn - enemycolumn) <= 1) {
                                if (herorow > enemyrow) {
                                    world.castAbility(hero, hero.getAbility(BLASTER_BOMB), enemyrow + 2, enemycolumn);
                                } else {
                                    world.castAbility(hero, hero.getAbility(BLASTER_BOMB), enemyrow - 2, enemycolumn);
                                }
                            } else {
                                if (herocolumn > enemycolumn) {
                                    world.castAbility(hero, hero.getAbility(BLASTER_BOMB), enemyrow, enemycolumn + 2);
                                } else {
                                    world.castAbility(hero, hero.getAbility(BLASTER_BOMB), enemyrow, enemycolumn - 2);

                                }
                            }
                        }
                    } else {
                        if (min <= 4) {
                            world.castAbility(hero, hero.getAbility(BLASTER_ATTACK), enemy[target].getCurrentCell());
                        } else if (min <= 5) {
                            if (Math.abs(herocolumn - enemycolumn) == 0) {
                                if (herorow > enemyrow) {
                                    world.castAbility(hero, hero.getAbility(BLASTER_ATTACK), enemyrow + 1, enemycolumn);
                                } else {
                                    world.castAbility(hero, hero.getAbility(BLASTER_ATTACK), enemyrow - 1, enemycolumn);
                                }
                            } else {
                                if (herocolumn > enemycolumn) {
                                    world.castAbility(hero, hero.getAbility(BLASTER_ATTACK), enemyrow, enemycolumn + 1);
                                } else {
                                    world.castAbility(hero, hero.getAbility(BLASTER_ATTACK), enemyrow, enemycolumn - 1);

                                }
                            }
                        }
                    }

                }
            }
        }


//        if (world.getCurrentTurn() == 4) {
//            Hero hero = heroes[0];
//            int x = hero.getCurrentCell().getColumn();
//            if (x > 10) {
//                x = x - 4;
//            } else if (x < 10) {
//                x = x + 4;
//                System.out.println();
//            }
//
//            world.castAbility(hero, hero.getAbility(BLASTER_DODGE), map.getCell(x, hero.getCurrentCell().getRow()));
//        }
//        if (world.getCurrentTurn() == 1) {
//            Hero hero = heroes[1];
//            int x = hero.getCurrentCell().getColumn();
//            if (x > 10) {
//                x = x - 4;
//            } else if (x < 10) {
//                x = x + 4;
//                System.out.println("d");
//            }
//
//            world.castAbility(hero, hero.getAbility(BLASTER_DODGE), map.getCell(x, hero.getCurrentCell().getRow()));
//        }
//        if (world.getCurrentTurn() == 2) {
//            Hero hero = heroes[2];
//            int x = hero.getCurrentCell().getColumn();
//            if (x > 10) {
//                x = x - 4;
//            } else if (x < 10) {
//                x = x + 4;
//                System.out.println("r");
//            }
//
//            world.castAbility(hero, hero.getAbility(BLASTER_DODGE), map.getCell(x, hero.getCurrentCell().getRow()));
//        }
//        if (world.getCurrentTurn() == 3) {
//            Hero hero = heroes[3];
//            int x = hero.getCurrentCell().getColumn();
//            if (x > 10) {
//                x = x - 4;
//            } else if (x < 10) {
//                x = x + 4;
//            }
//
//            world.castAbility(hero, hero.getAbility(BLASTER_DODGE), map.getCell(x, hero.getCurrentCell().getRow()));
//        }
//
//
//        for (int i = 0; i < 4; i++) {
//            System.out.println(doshman[i].toString());
//        }
//
//        for (int i = 0; i < 4; i++) {
//            Hero hero = heroes[i];
//
//
//            int min = world.manhattanDistance(doshman[0].getCurrentCell(), hero.getCurrentCell());
//            for (int j = 1; j < doshman.length; j++) {
//                if (world.manhattanDistance(doshman[j].getCurrentCell(), hero.getCurrentCell()) > min && i < 2) {
//                    id1 = j;
//                }
//                if (world.manhattanDistance(doshman[j].getCurrentCell(), hero.getCurrentCell()) > min && 1 < i) {
//                    id2 = j;
//                }
//            }
//
//
//            if (id1 != -1) {
//                if (hero.getAbility(BLASTER_BOMB).isReady()) {
//                    world.castAbility(hero, hero.getAbility(BLASTER_BOMB), doshman[id1].getCurrentCell());
//                } else {
//                    world.castAbility(hero, hero.getAbility(BLASTER_ATTACK), doshman[id1].getCurrentCell());
//                }
//            }
//            if (id2 != -1) {
//                if (hero.getAbility(BLASTER_BOMB).isReady()) {
//                    world.castAbility(hero, hero.getAbility(BLASTER_BOMB), doshman[id2].getCurrentCell());
//                    System.out.println("1");
//                } else {
//                    world.castAbility(hero, hero.getAbility(BLASTER_ATTACK), doshman[id2].getCurrentCell());
//                }
//            }
//        }
    }

}
