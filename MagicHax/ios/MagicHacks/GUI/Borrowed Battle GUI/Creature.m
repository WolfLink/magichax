//
//  Creature.m
//  PokeWatch
//
//  Created by Marc Davis on 3/31/15.
//  Copyright (c) 2015 Marc Davis. All rights reserved.
//

#import "Creature.h"

@implementation Creature
-(NSDictionary *)dictionaryValue {
    NSMutableArray *sendSpells = [[NSMutableArray alloc] initWithCapacity:self.spells.count];
    for (int c = 0; c < sendSpells.count; c++) {
        //[sendSpells addObject:@([(Move *)self.spells[c] moveID])];
    }
    return @{@"atk" : @(self.defaultAtk), @"def" : @(self.defaultDef), @"health" : @(self.maxHealth), @"mag" : @(self.defaultMag), @"speed" : @(self.defaultSpeed), @"type1" : @(self.primaryType), @"type2" : @(self.secondaryType), @"spells" : sendSpells};
}
-(id)initWithDictionary:(NSDictionary *)dict {
    self = [super init];
    if (self) {
        _primaryType = [dict[@"type1"] intValue];
        _secondaryType = [dict[@"type2"] intValue];
        _atk = [dict[@"atk"] intValue];
        _mag = [dict[@"mag"] intValue];
        _def = [dict[@"def"] intValue];
        _speed = [dict[@"speed"] intValue];
        _maxHealth = [dict[@"hp"] intValue];
        _health = _maxHealth;
        _defaultAtk = _atk;
        _defaultMag = _mag;
        _defaultDef = _def;
        _defaultSpeed = _speed;
        NSArray *reception = dict[@"spells"];
        _spells = [[NSMutableArray alloc] initWithCapacity:reception.count];
        /*for (int c = 0; c < reception.count; c++) {
            [_spells addObject:[Move moveWithID:[reception[c] intValue]]];
        }
        _activeMoves = [[NSMutableArray alloc] initWithCapacity:4];
        for (int c = 0; c < 4; c++) {
            [_activeMoves addObject:[self pickNewMove]];
        }*/
    }
    return self;
}
-(id)initWithTypes:(BattleType)t1 and:(BattleType)t2 andMoves:(NSMutableArray *)m {
    self = [super init];
    if (self) {
        _primaryType = t1;
        _secondaryType = t2;
        _spells = m;
        _activeMoves = [[NSMutableArray alloc] initWithCapacity:4];
        /*for (int c = 0; c < 4; c++) {
            [_activeMoves addObject:[self pickNewMove]];
        }*/
        
        self.health = 100;
        self.maxHealth = 100;
        self.atk = 25;
        self.mag = 25;
        self.def = 25;
        self.speed = 25;
    }
    return self;
}
/*-(Move *)drawMoveFromStack {
    Move *move;
    if (_spells.count < 1) {
        return [[Move alloc] init];
    }
    else {
        move = [_spells lastObject];
        [_spells removeLastObject];
        return move;
    }

}
-(Move *)useMove:(int)index{
    Move *m = _activeMoves[index];
    _activeMoves[index] = [self drawMoveFromStack];
    return m;
}
-(void)replaceMove:(int)index withMoveOfID:(int)ID {
    _activeMoves[index] = [Move moveWithID:ID];
    [_delegate didReplaceMoveAtIndex:index];
}
-(Move *)pickNewMove {
    
    Move *move;
    if (_spells.count < 1) {
        return [[Move alloc] init];
    }
    else {
        move = [_spells lastObject];
        [_spells removeLastObject];
        return move;
    }

}*/
-(UIImage *)displayImage {
    NSString *filename = @"test";
    if (_primaryType == _secondaryType) {
        switch (_primaryType) {
            case kBattleTypeFire:
                filename = @"fire";
                break;
            case kBattleTypeAir:
                filename = @"air";
                break;
            case kBattleTypeEarth:
                filename = @"earth";
                break;
            case kBattleTypePlant:
                filename = @"plant";
                break;
            case kBattleTypeWater:
                filename = @"water";
                break;
            case kBattleTypeElectric:
                filename = @"electric";
                break;
        }
    } else if (_primaryType == kBattleTypeFire || _secondaryType == kBattleTypeFire){
        BattleType other = _primaryType == kBattleTypeFire ? _secondaryType : _primaryType;
        switch (other) {
            case kBattleTypeElectric:
                filename = @"fireelectric";
                break;
            case kBattleTypeWater:
                filename = @"firewater";
                break;
            case kBattleTypePlant:
                filename = @"fireplant";
                break;
            case kBattleTypeEarth:
                filename = @"fireearth";
                break;
            case kBattleTypeAir:
                filename = @"fireair";
                break;
            default:
                break;
        }
    } else if (_primaryType == kBattleTypeWater || _secondaryType == kBattleTypeWater){
        BattleType other = _primaryType == kBattleTypeFire ? _secondaryType : _primaryType;
        switch (other) {
            case kBattleTypeElectric:
                filename = @"waterelectric";
                break;
            case kBattleTypePlant:
                filename = @"waterplant";
                break;
            case kBattleTypeEarth:
                filename = @"waterearth";
                break;
            case kBattleTypeAir:
                filename = @"waterair";
                break;
            default:
                break;
        }
    } else if (_primaryType == kBattleTypeElectric || _secondaryType == kBattleTypeElectric){
        BattleType other = _primaryType == kBattleTypeFire ? _secondaryType : _primaryType;
        switch (other) {
            case kBattleTypePlant:
                filename = @"electricplant";
                break;
            case kBattleTypeEarth:
                filename = @"electricearth";
                break;
            case kBattleTypeAir:
                filename = @"electricair";
                break;
            default:
                break;
        }
    } else if (_primaryType == kBattleTypeEarth || _secondaryType == kBattleTypeEarth){
        BattleType other = _primaryType == kBattleTypeFire ? _secondaryType : _primaryType;
        switch (other) {
            case kBattleTypePlant:
                filename = @"earthplant";
                break;
            case kBattleTypeAir:
                filename = @"earthair";
                break;
            default:
                break;
        }
    } else if (_primaryType == kBattleTypePlant || _secondaryType == kBattleTypePlant){
        BattleType other = _primaryType == kBattleTypeFire ? _secondaryType : _primaryType;
        switch (other) {
            case kBattleTypeAir:
                filename = @"plantair";
                break;
            default:
                break;
        }
    }
    return [UIImage imageNamed:filename];
}
bool checkCase(int a, int b, int c, int d) {
    return (a==c && b==d) || (a==d && b==c);
}
@end
