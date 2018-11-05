//
//  BattleMessenger.h
//  PokeWatch
//
//  Created by Marc Davis on 4/6/15.
//  Copyright (c) 2015 Marc Davis. All rights reserved.
//

#import <Foundation/Foundation.h>

@protocol MessengerDisplay <NSObject>

-(void)displayMessages;

@end

@interface BattleMessenger : NSObject 

+(void)addMessage:(NSString *)message;
+(NSString *)getNextMessage;
+(BOOL)isEmpty;
+(void)setDisplay:(id<MessengerDisplay>)display;
+(void)setDelay:(double)d;
+(double)getDelay;
@end
