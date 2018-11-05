//
//  Creature.h
//  PokeWatch
//
//  Created by Marc Davis on 3/31/15.
//  Copyright (c) 2015 Marc Davis. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>
#import "Types.h"
@class Move;

@interface Creature : NSObject {
}
-(NSDictionary *)dictionaryValue;
-(id)initWithDictionary:(NSDictionary *)dict;
-(id)initWithTypes:(BattleType)t1 and:(BattleType)t2 andMoves:(NSMutableArray *)m;
-(UIImage *)displayImage;
@property (nonatomic, readwrite) NSMutableArray *activeMoves;
@property (nonatomic, readwrite) NSString *title;
@property (nonatomic, readonly) BattleType primaryType;
@property (nonatomic, readonly) BattleType secondaryType;
@property (nonatomic, readonly) NSMutableArray *spells;

@property (nonatomic, readwrite) int health;
@property (nonatomic, readwrite) int maxHealth;
@property (nonatomic, readwrite) int speed;
@property (nonatomic, readwrite) int defaultSpeed;
@property (nonatomic, readwrite) int atk;
@property (nonatomic, readwrite) int defaultAtk;
@property (nonatomic, readwrite) int def;
@property (nonatomic, readwrite) int defaultDef;
@property (nonatomic, readwrite) int mag;
@property (nonatomic, readwrite) int defaultMag;
@end
