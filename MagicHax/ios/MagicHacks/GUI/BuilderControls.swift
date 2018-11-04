//
//  BuilderControls.swift
//  MagicHacks
//
//  Created by Marc Davis on 11/4/18.
//  Copyright © 2018 Marc Davis. All rights reserved.
//

import Foundation
import UIKit

class MCTypeSelectionView: UIView {
    var chosen: CreatureType
    private var buttons: [UIImageView]
    
    override init(frame: CGRect) {
        print("init with frame")
        buttons = [UIImageView(image: UIImage(named: "ari64"), highlightedImage: UIImage(named: "ari64-gray")),
                   UIImageView(image: UIImage(named: "earth64"), highlightedImage: UIImage(named: "earth64-gray")),
                   UIImageView(image: UIImage(named: "elec64"), highlightedImage: UIImage(named: "elec64-gray")),
                   UIImageView(image: UIImage(named: "fire64"), highlightedImage: UIImage(named: "fire64-gray")),
                   UIImageView(image: UIImage(named: "plant64"), highlightedImage: UIImage(named: "plant64-gray")),
                   UIImageView(image: UIImage(named: "water64"), highlightedImage: UIImage(named: "water64-gray"))]
        chosen = .air
        super.init(frame: frame)
        for view in buttons {
            self.addSubview(view)
        }
        self.backgroundColor = UIColor.clear
    }
    
    required init?(coder aDecoder: NSCoder) {
        buttons = [UIImageView(image: UIImage(named: "ari64"), highlightedImage: UIImage(named: "ari64-gray")),
                   UIImageView(image: UIImage(named: "earth64"), highlightedImage: UIImage(named: "earth64-gray")),
                   UIImageView(image: UIImage(named: "elec64"), highlightedImage: UIImage(named: "elec64-gray")),
                   UIImageView(image: UIImage(named: "fire64"), highlightedImage: UIImage(named: "fire64-gray")),
                   UIImageView(image: UIImage(named: "plant64"), highlightedImage: UIImage(named: "plant64-gray")),
                   UIImageView(image: UIImage(named: "water64"), highlightedImage: UIImage(named: "water64-gray"))]
        chosen = .air
        super.init(coder: aDecoder)
        for view in buttons {
            self.addSubview(view)
        }
        self.backgroundColor = UIColor.clear
    }
    
    override func layoutSubviews() {
        let size = min(self.frame.size.height, self.frame.size.width / 6)
        let types = CreatureType.allCases
        for (index, button) in buttons.enumerated() {
            button.frame = CGRect(x: (CGFloat(index) * self.frame.size.width / 6) + self.frame.size.width/12 - size/2, y: 0, width: size, height: size)
            button.isHighlighted = true
            if types[index] == chosen {
                button.isHighlighted = false
            }
        }
    }
    
    override func touchesEnded(_ touches: Set<UITouch>, with event: UIEvent?) {
        for touch in touches {
            let location = touch.location(in: self)
            for (index, button) in buttons.enumerated() {
                if button.frame.contains(location) {
                    buttons[chosen.rawValue].isHighlighted = true
                    chosen = CreatureType.allCases[index]
                    buttons[chosen.rawValue].isHighlighted = false
                    break
                }
            }
        }
    }
    
}
